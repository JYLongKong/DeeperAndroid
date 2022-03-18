#ifndef VULKANEXBASE_CUBE_H
#define VULKANEXBASE_CUBE_H

#include "ColorRect.h"
#include "DrawableObjectCommon.h"

class Cube {
 public:
  void drawSelf(VkCommandBuffer cmd,
                VkPipelineLayout &pipelineLayout,
                VkPipeline &pipeline,
                VkDescriptorSet *desSetPointer);

  Cube(VkDevice &device, VkPhysicalDeviceMemoryProperties &memoryroperties);

  ~Cube();
};

#endif //VULKANEXBASE_CUBE_H
