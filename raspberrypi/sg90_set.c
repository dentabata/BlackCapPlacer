#include <stdio.h>
#include <stdlib.h>
#include <wiringPi.h>

#define SERVO_PIN 1 // GPIO 18
#define BUTTON_PIN 27 // GPIO 16

volatile int button_pressed = 0;

void int_button(void) {
  pwmWrite(SERVO_PIN, 112);
  button_pressed = 1;
}

int main(int argc, char *argv[]){
  if (wiringPiSetup() == -1) {
    printf("Error: setup failed.\n");
    return -1;
  }

  pinMode(SERVO_PIN, PWM_OUTPUT);
  pinMode(BUTTON_PIN, INPUT);
  pwmSetMode(PWM_MODE_MS);
  pwmSetClock(400);
  pwmSetRange(1024);

  wiringPiISR(BUTTON_PIN, INT_EDGE_RISING, int_button);

  while (1) {
    delay(1000);
    if (button_pressed) {
      button_pressed = 0;
      delay(500);
      pwmWrite(SERVO_PIN, 0);
    }
  }
  return 0;
}