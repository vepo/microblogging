#!/bin/bash
Xvfb :1 -screen 0 1920x1080x24 &
export DISPLAY=:1
ng test --no-watch --code-coverage