#ifndef VULKANEXBASE_OBJOBJECT_H
#define VULKANEXBASE_OBJOBJECT_H

#include <vulkan/vulkan.h>
#include "vulkan_wrapper.h"
#include <string>

class DrawableObjectCommonLight {
 public:
  /// Sample4_2-需推送的常量数据数组的首地址指针
  float *pushConstantData;

  VkDevice *devicePointer;                      // 指向逻辑设备的指针
  float *vdata;                                 // 顶点数据数组首地址指针
  int vCount;                                   // 顶点数量
  VkBuffer vertexDatabuf;                       // 顶点数据缓冲
  VkDeviceMemory vertexDataMem;                 // 顶点数据所需设备内存
  VkDescriptorBufferInfo vertexDataBufferInfo;  // 顶点数据缓冲描述信息

  DrawableObjectCommonLight(float *vdataIn,
                            int dataByteCount,
                            int vCountIn,
                            VkDevice &device,
                            VkPhysicalDeviceMemoryProperties &memoryroperties);

  ~DrawableObjectCommonLight();

  /**
   * 绘制方法
   */
  void drawSelf(VkCommandBuffer &secondary_cmd,
                VkPipelineLayout &pipelineLayout,
                VkPipeline &pipeline,
                VkDescriptorSet *desSetPointer);
};

#endif
