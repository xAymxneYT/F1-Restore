from pathlib import Path
from urllib.request import urlretrieve, urlopen
from concurrent.futures import ThreadPoolExecutor
import json, zipfile, subprocess, os, hashlib, struct, io
root = Path(__file__).resolve().parent.parent
os.chdir(root)
deps = root / 'deps'
deps.mkdir(exist_ok=True)
urls = {
 'ecj.jar': 'https://repo.maven.apache.org/maven2/org/eclipse/jdt/ecj/3.37.0/ecj-3.37.0.jar',
 'mixin.jar': 'https://maven.fabricmc.net/net/fabricmc/sponge-mixin/0.15.0+mixin.0.8.7/sponge-mixin-0.15.0+mixin.0.8.7.jar',
 'asm.jar': 'https://repo.maven.apache.org/maven2/org/ow2/asm/asm/9.7.1/asm-9.7.1.jar',
 'asm-commons.jar': 'https://repo.maven.apache.org/maven2/org/ow2/asm/asm-commons/9.7.1/asm-commons-9.7.1.jar',
 'asm-tree.jar': 'https://repo.maven.apache.org/maven2/org/ow2/asm/asm-tree/9.7.1/asm-tree-9.7.1.jar',
 'calamus.jar': 'https://maven.ornithemc.net/releases/net/ornithemc/calamus-intermediary-gen2/1.8.9/calamus-intermediary-gen2-1.8.9.jar'
}
for name, url in urls.items():
    urlretrieve(url, deps / name)
with zipfile.ZipFile(deps / 'calamus.jar') as archive:
    (deps / 'calamus.tiny').write_bytes(archive.read('mappings/mappings.tiny'))
manifest = json.load(urlopen('https://piston-meta.mojang.com/mc/game/version_manifest_v2.json'))
metadata = json.load(urlopen(next(v['url'] for v in manifest['versions'] if v['id'] == '1.8.9')))
client = metadata['downloads']['client']
urlretrieve(client['url'], deps / 'client.jar')
assert hashlib.sha1((deps / 'client.jar').read_bytes()).hexdigest() == client['sha1']
libs = deps / 'mclibs'
libs.mkdir(exist_ok=True)
def fetch_library(library):
    artifact = library.get('downloads', {}).get('artifact')
    if artifact:
        urlretrieve(artifact['url'], libs / artifact['path'].split('/')[-1])
with ThreadPoolExecutor(max_workers=8) as pool:
    list(pool.map(fetch_library, metadata['libraries']))
# Pinned OneConfig API signatures for native panel integration. This never changes
# the user's installed OneConfig; the normalized jar is local compile input only.
release = json.load(urlopen('https://api.modrinth.com/v2/version/E8wxQeN5'))
artifact = next((f for f in release['files'] if f.get('primary')), release['files'][0])
urlretrieve(artifact['url'], deps / 'oneconfig-reference.jar')
seen = set()
with zipfile.ZipFile(deps / 'oneconfig-reference.jar') as bundle, zipfile.ZipFile(deps / 'oneconfig-compile.jar', 'w', zipfile.ZIP_DEFLATED) as target:
    for name in bundle.namelist():
        if not name.endswith('.jar') or not any(part in name for part in ['config-', 'config-impl', 'ui-1.', 'utils-1.', '1.8.9-ornithe', 'internal-', 'hud-', 'events-']):
            continue
        with zipfile.ZipFile(io.BytesIO(bundle.read(name))) as library:
            for entry in library.namelist():
                if not entry.endswith('.class') or entry in seen:
                    continue
                seen.add(entry)
                data = bytearray(library.read(entry))
                data[6:8] = struct.pack('>H', 61)
                target.writestr(entry, data)
urlretrieve('https://repo.maven.apache.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/2.2.21/kotlin-stdlib-2.2.21.jar', deps / 'kotlin-stdlib.jar')

classpath = os.pathsep.join(['deps/asm.jar', 'deps/asm-commons.jar'])
subprocess.run(['java', '-jar', 'deps/ecj.jar', '-17', '-proc:none', '-nowarn', '-cp', classpath, '-d', 'deps', 'tools/RemapGen.java'], check=True)
subprocess.run(['java', '-cp', os.pathsep.join(['deps', classpath]), 'RemapGen'], check=True)
print('Dependencies ready. Run sh F1Restore/build.sh')
