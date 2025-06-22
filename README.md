# 開発背景
ブラックキャップを家の隅々においてゴキブリを駆逐しましょう！ブラックキャップとは，家の中にゴキブリを駆除する毒餌剤のことです。ゴキブリを駆除するためにはゴキブリの生息場所にブラックキャップを置く必要がありますが，ベッドの下など人間が配置しにくい場所もあります。そのような場所にブラックキャップを配置するシステムを開発しました。

# 設計
## 全体構成
システム全体の構成を以下に示す。
```
図
```
ブラックキャップの配置はロボットにて行う。ロボットにはモータとWebカメラが搭載されている。これらの動作はRaspberry Pi上で制御する。Android上でコントローラアプリケーションを動作させ，Rasberry Pi上のPHPサーバで指示を受け付ける。Raspberry Pi上のWebカメラで撮影される動画は配信され，Androidアプリに表示される。

## Frontend

### 初期画面
- WebView：未実装。RaspberryPiからの映像配信がある場合はここに表示。
- IPアドレス：Rasberry PiのIPアドレスを入力
- ↑：[http://\<ip\>/~pi/forward.php]を送信
- →：[http://\<ip\>/~pi/right.php]を送信
- ←：[http://\<ip\>/~pi/left.php]を送信
- ↓：[http://\<ip\>/~pi/back.php]を送信
- ◻︎：[http://\<ip\>/~pi/stop.php]を送信
- DROP：[http://\<ip\>/~pi/drop.php]を送信
- 管理：管理画面へ遷移
<img src="https://github.com/user-attachments/assets/62a5e2da-bf8f-408f-acea-de9f16746231" width="150"/>

### 管理画面
この画面では保存された画像ファイルがリスト表示し，選択した画像を上部のImageViewで表示する。

- 間取りを追加：Androidの写真フォルダにアクセスし，選択した画像を名前をつけて保存
- 間取りを編集：画像が選択されている場合，画像編集画面へ遷移

<img src="https://github.com/user-attachments/assets/cc7abfb1-15bd-48b4-bb24-9cd109641f18" width="150"/>

### 画像編集画面
この画面では画像の編集を行う。画像の任意の点をタップすると赤い印がつけられる。ブラックキャップをおいた場所を記録するための機能である。

- 保存：現在の画像を上書き保存
- 削除：画像ファイルを完全に削除
- リセット：保存されていない変更をすべてリセット

<p float="left">
  <img src="https://github.com/user-attachments/assets/15fbc005-ee3d-4bd7-8598-65e460e4976b" width="150" />
  <img src="https://github.com/user-attachments/assets/678d91f2-42aa-49c3-b38a-7e8826a48871" width="150" />
</p>



## Backend

### 車両型ロボット
車両型ロボットでは対応するスクリプトを実行することで以下の動作を行う。

- 前進　 `./motor_pu2709 Forward `
- 後退 　`./motor_pu2709 Backward`
- 停止 　`./motor_pu2709 Stop`
- 右旋回 `./motor_pu2709 Right`
- 左旋回 `./motor_pu2709 Left`
- セット `sudo ./sg90 Set`
- 投下　 `sudo ./sg90 Drop`

#### ブラックキャップのセット
ブラックキャップをセットする様子を図に示す。

<img src="https://github.com/user-attachments/assets/9be6296d-9959-4314-be5b-ffb4ef6a617a" width="480px">
<img src="https://github.com/user-attachments/assets/16e939f5-029f-4a93-8d5e-245ab6327c56" width="480px">

赤いボタンを押すと `sudo ./sg90 Set` が実行され、板が固定される。

#### ブラックキャップの投下
投下コマンド `sudo ./sg90 Drop` が実行されるとサーボモータが回転し，図のように板の固定が外れることでブラックキャップが投下される。

<img src="https://github.com/user-attachments/assets/5db34401-7fd4-4391-a414-1932f2d78c65" width="480px">
<img src="https://github.com/user-attachments/assets/738862ca-25e3-45e1-a840-6ae83b985301" width="480px">
