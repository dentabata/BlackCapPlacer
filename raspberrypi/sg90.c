#include <stdio.h>
#include <stdlib.h>
#include <wiringPi.h>

#define SERVO_PIN 1 // GPIO 18

int main(int argc, char *argv[]){
  if (argc != 2) {
    printf("Usage: %s [Drop/Set]\n", argv[0]);
    return -1;
  }
  if (argv[1][0] != 'D' && argv[1][0] != 'd' && argv[1][0] != 'S' && argv[1][0] != 's') {
    printf("Error: argument must be specified as Drop or Set.\n");
    return -1;
  }
  if (wiringPiSetup() == -1) {
    printf("Error: setup failed.\n");
    return -1;
  }

  pinMode(SERVO_PIN, PWM_OUTPUT);
  pwmSetMode(PWM_MODE_MS);
  pwmSetClock(400);
  pwmSetRange(1024);

  if (argv[1][0] == 'D' || argv[1][0] == 'd') {
    // Drop
    pwmWrite(SERVO_PIN, 68);
  } else if (argv[1][0] == 'S' || argv[1][0] == 's') {
    // Set
    pwmWrite(SERVO_PIN, 112);
  }
  delay(500);
  pwmWrite(SERVO_PIN, 0);
  return 0;
}