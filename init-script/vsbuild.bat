chcp 65001
@echo off
rem chcp 65001 >nul
setlocal

echo ===== Visual Studio の起動確認 =====

:: Visual Studio（devenv.exe）が起動しているか確認する
tasklist | findstr /I "devenv.exe" >nul

if %errorlevel%==0 (
    echo.
    echo Visual Studio が起動中のため、処理を続行できません。
	chcp 65001 >nul
    echo Visual Studio を終了してから、再度このバッチを実行してください。
    echo.
    pause
    exit /b
)

echo Visual Studio は起動していません。処理を続行します…
echo.

rem ================================
rem 今日の日付を取得 (YYYYMMDDHHMM形式)
rem ================================
for /f "tokens=2 delims==." %%I in ('wmic os get LocalDateTime /value') do set datetime=%%I
set date=%datetime:~0,12%

rem ================================
rem 環境設定（MSBuildパス、構成、ログファイル）
rem ================================
set MSBUILD="C:\Program Files\Microsoft Visual Studio\2022\Enterprise\MSBuild\Current\Bin\MSBuild.exe"
set CONFIG=Release
set LOGFILE=buildlogs/build_log_%date%.txt
if not exist buildlogs (
    mkdir buildlogs
)

rem ================================
rem ログファイルを初期化
rem ================================
if exist %LOGFILE% del %LOGFILE%
echo 🔧 ビルド開始 >> %LOGFILE%
echo.

rem ================================
rem プロジェクト一覧（ビルド順）
rem ================================
set PROJECTS=^
 "C:\workspace\Clnt\AA\AA.csproj"^
 "C:\workspace\Clnt\AB\AB.csproj"^
 "C:\workspace\Clnt\AC\AC.csproj"^
 "C:\workspace\Clnt\AD\AD.csproj"^
 "C:\workspace\Clnt\AE\AE.csproj"^
 "C:\workspace\Clnt\BA\BA.csproj"^
 "C:\workspace\Clnt\CA\CA.csproj"^
 "C:\workspace\Clnt\CB\CB.csproj"^
 "C:\workspace\Clnt\CC\CC.csproj"^
 "C:\workspace\Clnt\CD\CD.csproj"^
 "C:\workspace\Clnt\CE\CE.csproj"^
 "C:\workspace\Clnt\DA\DA.csproj"^

rem ================================
rem 全プロジェクトをクリーン
rem ================================
echo 🧹 クリーン処理開始...
setlocal enabledelayedexpansion
set INDEX=0
for %%P in (%PROJECTS%) do (
    set /a INDEX+=1
	set "IDX=0!INDEX!"
	set "IDX=!IDX:~-2!"
    echo 🔸 Clean[!IDX!]: %%~nxP
    echo 🔸 Clean[!IDX!]: %%~nxP >> %LOGFILE%
    %MSBUILD% %%P /t:Clean /p:Configuration=%CONFIG% >> %LOGFILE% 2>&1
    if errorlevel 1 (
        echo ❌ クリーン失敗: %%~nxP
        echo ❌ クリーン失敗: %%~nxP >> %LOGFILE%
        goto :error
    )
)

rem ================================
rem プロジェクトを順にビルド
rem ================================
echo 🔨 ビルド処理開始...
setlocal enabledelayedexpansion
set INDEX=0
for %%P in (%PROJECTS%) do (
    set /a INDEX+=1
	set "IDX=0!INDEX!"
	set "IDX=!IDX:~-2!"
    echo 🔸 Build[!IDX!]: %%~nxP
    echo 🔸 Build[!IDX!]: %%~nxP >> %LOGFILE%
    %MSBUILD% %%P /p:Configuration=%CONFIG% >> %LOGFILE% 2>&1
    if errorlevel 1 (
        echo ❌ ビルド失敗: %%~nxP
        echo ❌ ビルド失敗: %%~nxP >> %LOGFILE%
        goto :error
    )
)

rem ================================
rem 完了メッセージ
rem ================================
echo.
echo ✅ すべてのプロジェクトが正常にビルドされました！
chcp 65001 >nul
echo ✅ ログファイル：%LOGFILE%

echo Visual Studio を起動します…
:: ▼ Visual Studio 2022 の起動（パスは環境に応じて変更可）
start "" "C:\Program Files\Microsoft Visual Studio\2022\Enterprise\Common7\IDE\devenv.exe"
echo 起動しました。
goto :end

rem ================================
rem エラー処理 exit /b 1
rem ================================
:error
echo.
echo ❌ ビルド中にエラーが発生しました。詳細はログファイルを確認してください。
chcp 65001 >nul
echo ❌ ログファイル：%LOGFILE%
goto :end

:end
pause
