:: renames .png files in a folder by replacing a string in them
@echo off
SETLOCAL ENABLEDELAYEDEXPANSION
SET old=frozen_tree
SET new=frozen_elm
for /f "tokens=*" %%f in ('dir /b *.png') do (
  SET newname=%%f
  SET newname=!newname:%old%=%new%!
  move "%%f" "!newname!"
)