#!/bin/bash

# チェック：引数の数
if [ $# -ne 1 ]; then
  echo "Usage: $0 <your_password>"
  exit 1
fi

NEW_PASSWORD="$1"

# .php ファイルを検索
for file in *.php; do
  if [ -f "$file" ]; then
    echo "Updating password in $file"
    # sed を使って PASSWORD 定義行を書き換え
    sed -i -E "s|(define\(\"PASSWORD\", \").*?(\\"\);)|\1$NEW_PASSWORD\2|" "$file"
  fi
done

echo "All PASSWORD values updated"
