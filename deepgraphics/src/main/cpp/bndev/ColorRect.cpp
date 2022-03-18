#include <vector>
#include <cmath>
#include <string>

#include "ColorRect.h"

float *ColorRect::vdata;
int ColorRect::dataByteCount;
int ColorRect::vCount;

void ColorRect::genVertexData() {
  vCount = 6;
  dataByteCount = vCount * 6 * sizeof(float);
  vdata = new float[vCount * 6]{
      0, 0, 0, 1, 1, 1,
      30, 30, 0, 0, 0, 1,
      -30, 30, 0, 0, 0, 1,
      -30, -30, 0, 0, 0, 1,
      30, -30, 0, 0, 0, 1,
      30, 30, 0, 0, 0, 1
  };
}
