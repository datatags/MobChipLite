#!/bin/bash
echo "[MobChip JavaDoc Builder] Starting..." && rm -rf docs/bukkit && rm -rf docs/base && mkdir docs/base && mkdir docs/bukkit && echo "[MobChip JavaDoc Builder] Injecting..." && cp -R mobchip-base/target/apidocs/* docs/base && cp -R mobchip-bukkit/target/apidocs/* docs/bukkit && echo "[MobChip JavaDoc Builder] Committing..." && git config --local user.email "action@github.com" && git config --local user.name "GitHub Actions" && && git add docs/bukkit && git add docs/base && git commit -m "Update Javadocs" && git push && echo "[MobChip JavaDoc Builder] Done!"
