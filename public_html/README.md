# public_html の構成

## 配置場所
```bash
cd ~pi
```

## web サーバ上に公開(apache2)
apache2 のインストール
```bash
sudo apt-get install apache2
```
public_html を公開
```bash
sudo a2enmod usedir
sudo systemctl restart apache2
sudo chmod 755 /home/pi
```

テスト
http://<ip>/~pi/
`Hello!`

## .php ファイルの詳細
ssh 接続を行うため、パスワードを自身で設定する必要がある。
以下のスクリプトを実行するとパスワードをまとめて変更できる
```bash
chmod +x exchange_password.sh
./exchange_password.sh your_password
```

各php ファイルがweb 上で開かれると、同じ配下の実行ファイルに引き数を与えて実行。<br>
*実行ファイルはまだ置けていません
forward.php -> motor_pu2709 Forward<br>
back.php    -> motor_pu2709 Backward<br>
right.php   -> motor_pu2709 Right<br>
left.php    -> motor_pu2709 Left<br>
stop.php    -> motor_pu2709 Stop<br>
<br>
drop.php    -> set90 Drop<br>
set.php     -> set90 Set<br>

- (例）正常に実行されると、web ページに以下のように表示される<br>
```
connect
/usr/bin/sudo /home/pi/public_html/motor_ou2709 Left
```


