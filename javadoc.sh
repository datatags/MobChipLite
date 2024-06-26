#!/bin/bash

git config --local user.email "action@github.com"
git config --local user.name "GitHub Actions"
git fetch origin gh-pages

echo "[MobChip JavaDoc Builder] Starting..."

rm -rf docs/

mkdir ./docs

echo "[MobChip JavaDoc Builder] Injecting..."

cp -R build/docs/javadoc/* docs/

git switch -f gh-pages

for dir in ./*
do
  if [ "$dir" == "./docs" ]; then
    continue
  fi

  rm -rf "$dir"
done

cp -Rfv ./docs/* ./
rm -rf ./docs

echo "mobchip.gmitch215.xyz" > CNAME
echo "[MobChip JavaDoc Builder] Committing..."

git add .
git commit -m "Update JavaDocs ($1)"
git push -f origin gh-pages

echo "[MobChip JavaDoc Builder] Done!"
