#!/bin/sh
set -eu
cd "$(dirname "$0")"
rm -rf build/classes
mkdir -p build/classes
build_cp="deps/minecraft-gen2.jar:deps/mixin.jar:deps/oneconfig-compile.jar:deps/kotlin-stdlib.jar"
for dep in deps/mclibs/*.jar; do build_cp="$build_cp:$dep"; done
java -jar deps/ecj.jar -8 -proc:none -nowarn -cp "$build_cp" -d build/classes $(find src/main/java -name '*.java')
python3 - <<'PACK'
import pathlib,zipfile
pathlib.Path('build/libs').mkdir(parents=True, exist_ok=True)
with zipfile.ZipFile('build/libs/f1-restore-1.8.9-ornithe-1.4.1.jar','w',zipfile.ZIP_DEFLATED) as z:
 for root in [pathlib.Path('build/classes'),pathlib.Path('src/main/resources')]:
  for p in root.rglob('*'):
   if p.is_file(): z.write(p,p.relative_to(root))
PACK
