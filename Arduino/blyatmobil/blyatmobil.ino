#define ENG1	11
#define ENG2	10
#define IN1		13
#define IN2		12
#define IN3		8
#define IN4		7

#define LEFT	true
#define RIGHT	false

void setup() {
	Serial.begin(9600);

	pinMode(ENG1, OUTPUT);
	pinMode(ENG2, OUTPUT);

	pinMode(IN1, OUTPUT);
	pinMode(IN2, OUTPUT);
	pinMode(IN3, OUTPUT);
	pinMode(IN4, OUTPUT);

	digitalWrite(IN1, LOW);
	digitalWrite(IN2, LOW);
	digitalWrite(IN3, LOW);
	digitalWrite(IN4, LOW);
	analogWrite(ENG1, 1);
	analogWrite(ENG2, 1);
}


void Lengine(int which, int speed, bool direction) {
	if (speed > 255)
		speed = 255;
	//--
	if (speed <= 0)
		speed = 1;
	//--
	
	if (direction) {
		digitalWrite(IN1, HIGH);
		digitalWrite(IN2, LOW);
	} else {
		digitalWrite(IN1, LOW);
		digitalWrite(IN2, HIGH);
	}
	
	analogWrite(which, speed);
}
void Rengine(int which, int speed, bool direction) {
	if (speed > 255)
		speed = 255;
	//--
	if (speed <= 0)
		speed = 1;
	//--
	
	if (direction) {
		digitalWrite(IN3, HIGH);
		digitalWrite(IN4, LOW);
	} else {
		digitalWrite(IN3, LOW);
		digitalWrite(IN4, HIGH);
	}
	
	analogWrite(which, speed);
}

bool left = false;
bool right = false;

bool lDir = true;
bool rDir = true;

void loop() {
	int recv = Serial.read();
	if (recv != -1) {
		Serial.println(char(recv));
		if (char(recv) == 'a') {//prawo
			//engine(ENG1, 255, true);
			left = true;
			right = true;
			lDir = false;
			rDir = true;
		}
		if (char(recv) == 'b') {//lewo
			//engine(ENG1, 1, true);
			left = true;
			right = true;
			lDir = true;
			rDir = false;
		}
		if (char(recv) == 'c') {//gaz
			//engine(ENG1, 1, true);
			if (right || left) {
				right	= false;
				left	= false;
			} else {
				right	= true;
				left	= true;
				rDir = true;
				lDir = true;
			}
		}
		if (char(recv) == 'd') {//tyl
			//engine(ENG1, 1, true);
			if (right || left) {
				right	= false;
				left	= false;
			} else {
				right	= true;
				left	= true;
				rDir = false;
				lDir = false;
			}
		}
		if (left) {
			Lengine(ENG1, 255, lDir);
		} else {
			Lengine(ENG1, 1, lDir);
		}
		if (right) {
			Rengine(ENG2, 255, rDir);
		} else {
			Rengine(ENG2, 1, rDir);
		}
	}
}
