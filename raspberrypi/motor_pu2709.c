#include <stdio.h>
#include <stdlib.h>

#include <wiringPi.h>

int GPIO_PIN[] = {30, 31, 8, 9, 7, 21, 22, 11, 10, 13, 12, 14, 26, 23, 15, 16, 27, 0, 1, 24, 28, 29, 3, 4, 5, 6, 25, 2};
int MOTOR_1A = 5;
int MOTOR_1B = 6;
int MOTOR_2A = 13;
int MOTOR_2B = 19;

int STOP = 0;
int FORWARD = 1;
int BACKWARD = 2;
int LEFT = 3;
int RIGHT = 4;

void forward(void) {
  digitalWrite(GPIO_PIN[MOTOR_1A], HIGH);
  digitalWrite(GPIO_PIN[MOTOR_1B], LOW);
  digitalWrite(GPIO_PIN[MOTOR_2A], HIGH);
  digitalWrite(GPIO_PIN[MOTOR_2B], LOW);
}
void backward(void) {
  digitalWrite(GPIO_PIN[MOTOR_1A], LOW);
  digitalWrite(GPIO_PIN[MOTOR_1B], HIGH);
  digitalWrite(GPIO_PIN[MOTOR_2A], LOW);
  digitalWrite(GPIO_PIN[MOTOR_2B], HIGH);
}
void left(void) {
  digitalWrite(GPIO_PIN[MOTOR_1A], LOW);
  digitalWrite(GPIO_PIN[MOTOR_1B], HIGH);
  digitalWrite(GPIO_PIN[MOTOR_2A], HIGH);
  digitalWrite(GPIO_PIN[MOTOR_2B], LOW);
}
void right(void) {
  digitalWrite(GPIO_PIN[MOTOR_1A], HIGH);
  digitalWrite(GPIO_PIN[MOTOR_1B], LOW);
  digitalWrite(GPIO_PIN[MOTOR_2A], LOW);
  digitalWrite(GPIO_PIN[MOTOR_2B], HIGH);
}
void stop(void) {
  digitalWrite(GPIO_PIN[MOTOR_1A], LOW);
  digitalWrite(GPIO_PIN[MOTOR_1B], LOW);
  digitalWrite(GPIO_PIN[MOTOR_2A], LOW);
  digitalWrite(GPIO_PIN[MOTOR_2B], LOW);
}

int main(int argc, char *argv[]) {
  if (argc != 2) {
    printf("Usage: %s [Stop / Forward / Backward / Left / Right]\n", argv[0]);
    return -1;
  }

  int stat = (argv[1][0] == 'S' || argv[1][0] == 's') ? STOP :
    (argv[1][0] == 'F' || argv[1][0] == 'f') ? FORWARD :
    (argv[1][0] == 'B' || argv[1][0] == 'b') ? BACKWARD :
    (argv[1][0] == 'L' || argv[1][0] == 'l') ? LEFT :
    (argv[1][0] == 'R' || argv[1][0] == 'r') ? RIGHT : -1;

  if (stat == -1) {
    printf("Error: MOTOR_STAT must be specified as Stop, Forward, Backward, Left, or Right.\n");
    return -1;
  }

  if (wiringPiSetup() == -1) {
    printf("Error: setup failed.\n");
    return -1;
  }

  pinMode(GPIO_PIN[MOTOR_1A], OUTPUT);
  pinMode(GPIO_PIN[MOTOR_1B], OUTPUT);
  pinMode(GPIO_PIN[MOTOR_2A], OUTPUT);
  pinMode(GPIO_PIN[MOTOR_2B], OUTPUT);

  int current_state = -1;
  int m_1a = digitalRead(GPIO_PIN[MOTOR_1A]);
  int m_1b = digitalRead(GPIO_PIN[MOTOR_1B]);
  int m_2a = digitalRead(GPIO_PIN[MOTOR_2A]);
  int m_2b = digitalRead(GPIO_PIN[MOTOR_2B]);
  if (m_1a == HIGH && m_1b == LOW && m_2a == HIGH && m_2b == LOW) {
    current_state = FORWARD;
  } else if (m_1a == LOW && m_1b == HIGH && m_2a == LOW && m_2b == HIGH) {
    current_state = BACKWARD;
  } else if (m_1a == LOW && m_1b == HIGH && m_2a == HIGH && m_2b == LOW) {
    current_state = LEFT;
  } else if (m_1a == HIGH && m_1b == LOW && m_2a == LOW && m_2b == HIGH) {
    current_state = RIGHT;
  } else if (m_1a == LOW && m_1b == LOW && m_2a == LOW && m_2b == LOW) {
    current_state = STOP;
  }

  if (current_state == -1) {
    printf("Warn: Current motor state is unknown.\n");
  }
  if (current_state == stat) {
    printf("Info: Current motor state is already %s.\n", argv[1]);
    return 0;
  }

  stop();
  delay(10);

  if (stat == STOP) {
    stop();
  } else if (stat == FORWARD) {
    forward();
  } else if (stat == BACKWARD) {
    backward();
  } else if (stat == LEFT) {
    left();
  } else if (stat == RIGHT) {
    right();
  }
  return 0;
}
