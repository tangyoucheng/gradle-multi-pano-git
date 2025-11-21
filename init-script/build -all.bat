chcp 65001
@echo off
rem chcp 65001 >nul
setlocal

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
 "C:\workspace_phs\phs-scaw\FW\Clnt\Client\Client.csproj"^
 "C:\workspace_phs\phs-scaw\FW\Clnt\Common\Common.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\ScawSecurity\ScawSecurity\ScawSecurity.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\ScawResources\ScawResources\ScawResources.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\ScawCommon\ScawCommon\ScawCommon.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\SebnCommon\SebnCommon.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Zaim\ZaimCommon\ZaimCommon.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\AA\AA.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\AB\AB.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\AC\AC.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\AD\AD.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\AE\AE.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\BA\BA.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\CA\CA.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\CB\CB.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\CC\CC.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\CD\CD.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\CE\CE.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\DA\DA.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Zaim\KA\KA.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Zaim\KC\KC.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Zaim\KD\KD.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Zaim\KE\KE.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\Main\MainAs.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\Menu\Menu.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\YA\YA.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\YB\YB.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\YC\YC.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\YD\YD.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\YE\YE.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\ZA\ZA.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Zaim\ZC\ZC.csproj"^
 "C:\workspace_phs\phs-scaw\Clnt\Sebn\Main\Main.csproj"

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
