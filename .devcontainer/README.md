# Dev Container Setup

このプロジェクトはVisual Studio CodeのDev Containers機能に対応しています。
Docker を使用した完全な開発環境をセットアップできます。

## 必要な環境

- **Visual Studio Code** (最新版)
- **Dev Containers 拡張機能**（VS Codeで自動インストール）
- **Docker Desktop** または **Docker Engine**

### インストール

#### 1. VS Code 拡張機能のインストール

VS Code を開き、以下の拡張機能をインストール：
- `Dev Containers` (Microsoft)
- `Docker` (Microsoft) - オプション

#### 2. Docker のインストール

- **Windows/Mac**: [Docker Desktop](https://www.docker.com/products/docker-desktop)
- **Linux**: [Docker Engine](https://docs.docker.com/engine/install/)

## 使い方

### 方法1: VS Code コマンドパレットから（推奨）

1. VS Code でプロジェクトを開く
2. `F1` キーを押してコマンドパレットを開く
3. `"Reopen in Container"` を入力して実行
4. Docker イメージが自動的にビルドされ、コンテナが起動します

### 方法2: Docker CLIから

```bash
# コンテナをビルド
docker build -f .devcontainer/Dockerfile -t springboot-dev .

# コンテナを起動
docker run -it --rm \
  -v $(pwd):/workspace \
  -v ~/.m2:/home/vscode/.m2 \
  -p 8080:8080 \
  -p 8443:8443 \
  springboot-dev
```

## 環境の詳細

### インストール済みツール

- **Java 17** - OpenJDK
- **Maven** - ビルドツール
- **Git** - バージョン管理
- **curl, wget, zip, unzip** - ユーティリティ
- **Alpine Linux** - 軽量な Linux OS

**注**: Codespaces では Alpine Linux を使用しています（軽量で高速）

### VS Code 拡張機能

自動的にインストールされる拡張機能：

- **Extension Pack for Java** - Java開発の統合パック
  - Language Support for Java (Red Hat)
  - Debugger for Java
  - Test Runner for Java
  - Maven for Java
  - Visual Studio IntelliCode

- **Spring Boot Extension Pack** - Spring Boot開発向け
  - Spring Boot Dashboard
  - Spring Initializr

- **HTML/CSS サポート**
  - HTML CSS Support
  - CSS Class Completion

### ポートマッピング

以下のポートは自動的にフォワードされます：

| ポート | 説明 |
|--------|------|
| 8080   | Spring Boot アプリケーション (HTTP) |
| 8443   | Spring Boot アプリケーション (HTTPS) |

### ボリュームマウント

- **ワークスペース**: `/workspace` - プロジェクトディレクトリ
- **Maven キャッシュ**: `/root/.m2` - Maven リポジトリキャッシュ

## よくある操作

### プロジェクトをビルド

コンテナ内のターミナルで：

```bash
mvn clean install
```

### Spring Boot を実行

```bash
mvn spring-boot:run
```

### テストを実行

```bash
mvn test
```

### ホストからアクセス

Spring Boot が起動したら、ホスト側のブラウザで以下にアクセス：

- **ホームページ**: http://localhost:8080/
- **メインページ**: http://localhost:8080/main

## トラブルシューティング

### Docker デーモンが起動していない

**Windows/Mac**:
- Docker Desktop を起動してください

**Linux**:
```bash
sudo systemctl start docker
```

### メモリ不足エラー

`devcontainer.json` の `JAVA_TOOL_OPTIONS` を調整：

```json
"remoteEnv": {
  "JAVA_TOOL_OPTIONS": "-Xmx1024m"
}
```

### Maven ダウンロード速度が遅い

Maven キャッシュがホストと共有されるため、初回は時間がかかります。
2回目以降は高速化されます。

### コンテナから抜ける

VS Code のリモートウィンドウを閉じるか、`F1` → `Reopen Folder Locally`

## Codespaces での使用

### Codespaces で起動する方法

1. GitHub でリポジトリを開く
2. **Code** ボタン → **Codespaces** → **Create codespace on main**
3. Codespaces が自動的に devcontainer.json を読み込む
4. 環境が構築される（3-5 分）

### Codespaces トラブルシューティング

#### 「mvn: command not found」エラー

**原因**: Maven がインストールされていない（Codespaces のキャッシュが古い場合など）

**解決方法**:

Alpine Linux の場合（Codespaces）:
```bash
# ターミナルで手動で再インストール
apk update
apk add --no-cache maven openjdk17
```

Debian/Ubuntu の場合（ローカル Dev Containers）:
```bash
apt-get update && apt-get install -y maven
```

または Codespace を再作成:
```
GitHub → Code → Codespaces → 既存の Codespace を Delete → 新規作成
```

#### ビルドが遅い、またはタイムアウト

**原因**: Maven 依存関係のダウンロード（初回時間がかかる）

**解決方法**:
```bash
# 時間を置いて再実行
mvn clean install

# または手動でキャッシュをクリア
rm -rf ~/.m2/repository
mvn clean install
```

#### ポート 8080 にアクセスできない

**確認方法**:
1. Codespaces のターミナルで `mvn spring-boot:run` を実行
2. VS Code 下部の通知を確認（ポートが自動フォワードされる）
3. 表示される URL をクリック

#### Codespaces が起動しない

**解決方法**:
1. GitHub でこの Codespace を削除
2. 新しい Codespace を作成
3. Dockerfile が正しく解析されるのを待つ

## 利点

✅ **環境の統一** - チーム全体で同じ開発環境  
✅ **セットアップ不要** - Dockerイメージに依存関係をすべて含める  
✅ **ホストマシン汚染なし** - すべて Docker 内で隔離  
✅ **簡単なクリーンアップ** - コンテナ削除で完全にクリア  
✅ **VS Code 統合** - シームレスなワークフロー

## 参考リンク

- [VS Code Dev Containers](https://code.visualstudio.com/docs/devcontainers/containers)
- [Dev Container 仕様](https://containers.dev/)
- [Docker 公式ドキュメント](https://docs.docker.com/)