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
- **Maven 3.9.0** - ビルドツール
- **Git** - バージョン管理
- **curl, wget** - ダウンロードツール

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