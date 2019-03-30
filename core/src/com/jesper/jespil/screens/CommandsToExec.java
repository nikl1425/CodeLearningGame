package com.jesper.jespil.screens;

public class CommandsToExec extends com.strongjoshua.console.CommandExecutor {

        private float startPosX;
        private float startPosY;
        private float destPosX;
        private float destPosY;
        private boolean executed = false;

        public CommandsToExec(float startPosX, float startPosY, float destPosX, float destPosY) {
            this.startPosX = startPosX;
            this.startPosY = startPosY;
            this.destPosX = destPosX;
            this.destPosY = destPosY;
        }

        public float getStartPosX() {
            return startPosX;
        }

        public void setStartPosX(float startPosX) {
            this.startPosX = startPosX;
        }

        public float getStartPosY() {
            return startPosY;
        }

        public void setStartPosY(float startPosY) {
            this.startPosY = startPosY;
        }

        public float getDestPosX() {
            return destPosX;
        }

        public void setDestPosX(float destPosX) {
            this.destPosX = destPosX;
        }

        public float getDestPosY() {
            return destPosY;
        }

        public void setDestPosY(float destPosY) {
            this.destPosY = destPosY;
        }

        public boolean isExecuted() {
            return executed;
        }

        public void setExecuted(boolean executed) {
            this.executed = executed;
        }
    }

