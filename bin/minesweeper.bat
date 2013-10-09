REM
REM  LICENSE
REM
REM "THE BEER-WARE LICENSE" (Revision 43):
REM "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
REM As long as you retain this notice you can do whatever you want with
REM this stuff. If we meet some day, and you think this stuff is worth it,
REM you can buy me a non alcohol-free beer in return.
REM
REM Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
REM

@echo off
set d=%~dp0

setx MINESWEEPER_JAVAFX=false

start javaw -jar %d%minesweeper.jar %*
