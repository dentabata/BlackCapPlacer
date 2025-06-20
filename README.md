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
