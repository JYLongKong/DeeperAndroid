#include <cmath>

#include "BeltData.h"
#include "CircleData.h"

float *CircleData::vdata;
int CircleData::dataByteCount;
int CircleData::vCount;
void CircleData::genVertexData() {
  int n = 10;
  vCount = n + 2;
  dataByteCount = vCount * 6 * sizeof(float);
  vdata = new float[vCount * 6];
  float angdegSpan = 360.0f / n;
  int count = 0;
  vdata[count++] = 0;
  vdata[count++] = 0;
  vdata[count++] = 0;
  vdata[count++] = 1;
  vdata[count++] = 1;
  vdata[count++] = 1;
  for (float angdeg = 0; ceil(angdeg) <= 360; angdeg += angdegSpan) {
    double angrad = BeltData::toRadians(angdeg);
    vdata[count++] = (float) (-50 * sin(angrad));
    vdata[count++] = (float) (50 * cos(angrad));
    vdata[count++] = 0;
    vdata[count++] = 0;
    vdata[count++] = 1;
    vdata[count++] = 0;
  }
}
