#include <vector>
#include <cmath>
#include <string>

#include "BeltData.h"

const double
    PI = 3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170680;

float *BeltData::vdata;
int BeltData::dataByteCount;
int BeltData::vCount;

double BeltData::toRadians(double d) {
  double e = d * PI / 180;
  return e;
}

void BeltData::genVertexData() {
  int n = 6;
  vCount = 2 * (n + 1);
  dataByteCount = vCount * 6 * sizeof(float);
  float angdegBegin = -90;
  float angdegEnd = 90;
  float angdegSpan = (angdegEnd - angdegBegin) / n;
  vdata = new float[vCount * 6];
  int count = 0;
  for (float angdeg = angdegBegin; angdeg <= angdegEnd; angdeg += angdegSpan) {
    double angrad = toRadians(angdeg);
    vdata[count++] = (float) (-0.6f * 50 * sin(angrad));
    vdata[count++] = (float) (0.6f * 50 * cos(angrad));
    vdata[count++] = 0;
    vdata[count++] = 1;
    vdata[count++] = 1;
    vdata[count++] = 1;
    vdata[count++] = (float) (-50 * sin(angrad));
    vdata[count++] = (float) (50 * cos(angrad));
    vdata[count++] = 0;
    vdata[count++] = 0;
    vdata[count++] = 1;
    vdata[count++] = 1;
  }
}
