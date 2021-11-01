#include "DrawableObjectCommon.h"
#include "mylog.h"
#include <assert.h>
#include "HelpFunction.h"
#include "MatrixState3D.h"
#include <string.h>

DrawableObjectCommonLight::DrawableObjectCommonLight(float *vdataIn,
                                                     int dataByteCount,
                                                     int vCountIn,
                                                     VkDevice &device,
                                                     VkPhysicalDeviceMemoryProperties &memoryroperties) {
  this->devicePointer = &device;                                          // 接收逻辑设备指针并保存
  this->vdata = vdataIn;                                                  // 接收顶点数据数组首地址指针并保存
  this->vCount = vCountIn;                                                // 接收顶点数量并保存

  VkBufferCreateInfo buf_info = {};                                       // 构建缓冲创建信息结构体实例
  buf_info.sType = VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO;                  // 设置结构体类型
  buf_info.pNext = nullptr;                                               // 自定义数据的指针
  buf_info.usage = VK_BUFFER_USAGE_VERTEX_BUFFER_BIT;                     // 缓冲的用途为顶点数据
  buf_info.size = dataByteCount;                                          // 设置数据总字节数
  buf_info.queueFamilyIndexCount = 0;                                     // 队列家族数量(共享模式EXCLUSIVE时为0)
  buf_info.pQueueFamilyIndices = nullptr;                                 // 队列家族索引列表(共享模式EXCLUSIVE时为空)
  buf_info.sharingMode = VK_SHARING_MODE_EXCLUSIVE;                       // 共享模式(表示缓冲不允许被多个队列家族的队列访问)
  buf_info.flags = 0;                                                     // 标志
  VkResult result = vk::vkCreateBuffer(device, &buf_info, nullptr, &vertexDatabuf); // 创建缓冲
  assert(result == VK_SUCCESS);                                           // 检查缓冲创建是否成功

  // 注：缓冲创建后只是一种逻辑存在，Vulkan并没有自动为其在设备内存中开辟存储空间，还需要
  // 为缓冲分配匹配的设备内存，并将设备内存与缓冲绑定才真正确定缓冲在设备内存中的存储位置
  VkMemoryRequirements mem_reqs;                                          // 缓冲内存需求
  vk::vkGetBufferMemoryRequirements(device, vertexDatabuf, &mem_reqs);    // 获取缓冲内存需求
  assert(dataByteCount <= mem_reqs.size);                                 // 检查内存需求获取是否正确

  VkMemoryAllocateInfo alloc_info = {};                                   // 构建内存分配信息结构体实例
  alloc_info.sType = VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO;              // 结构体类型
  alloc_info.pNext = nullptr;                                             // 自定义数据的指针
  alloc_info.memoryTypeIndex = 0;                                         // 内存类型索引
  alloc_info.allocationSize = mem_reqs.size;                              // 内存总字节数
  VkFlags requirements_mask = VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT         // 需要的内存类型掩码
      | VK_MEMORY_PROPERTY_HOST_COHERENT_BIT;                             // 该组合表示分配的设备内存可以被CPU访问，同时保证CPU与GPU访问的一致性
  bool flag = memoryTypeFromProperties(                                   // 获取所需内存类型索引
      memoryroperties, mem_reqs.memoryTypeBits, requirements_mask, &alloc_info.memoryTypeIndex);
  if (flag) {
    LOGI("confirm memory type success, memoryTypeIndex = %d", alloc_info.memoryTypeIndex);
  } else {
    LOGE("confirm memory type failed!");
  }
  result = vk::vkAllocateMemory(device, &alloc_info, nullptr, &vertexDataMem); // 为顶点数据缓冲分配内存
  assert(result == VK_SUCCESS);                                           // 检查内存分配是否成功

  // 将数据送入设备内存
  uint8_t *pData;                                                         // CPU访问时的辅助指针
  result = vk::vkMapMemory(device, vertexDataMem, 0, mem_reqs.size, 0, (void **) &pData); // 将设备内存映射为可供CPU访问
  assert(result == VK_SUCCESS);                                           // 检查映射是否成功
  memcpy(pData, vdata, dataByteCount);                                    // 将顶点数据(坐标、颜色)复制进设备内存
  vk::vkUnmapMemory(device, vertexDataMem);                               // 解除内存映射

  result = vk::vkBindBufferMemory(device, vertexDatabuf, vertexDataMem, 0); // 绑定内存与缓冲
  assert(result == VK_SUCCESS);

  vertexDataBufferInfo.buffer = vertexDatabuf;                            // 指定数据缓冲
  vertexDataBufferInfo.offset = 0;                                        // 数据缓冲起始偏移量
  vertexDataBufferInfo.range = mem_reqs.size;                             // 数据缓冲所占字节数
}

DrawableObjectCommonLight::~DrawableObjectCommonLight() {
  delete vdata;                                                           // 释放指针内存
  vk::vkDestroyBuffer(*devicePointer, vertexDatabuf, nullptr);            // 销毁顶点缓冲
  vk::vkFreeMemory(*devicePointer, vertexDataMem, nullptr);               // 释放设备内存
}

/**
 * 绘制的方法
 */
void DrawableObjectCommonLight::drawSelf(VkCommandBuffer &cmd,
                                         VkPipelineLayout &pipelineLayout,
                                         VkPipeline &pipeline,
                                         VkDescriptorSet *desSetPointer) {
  // VK_PIPELINE_BIND_POINT_GRAPHICS表示绑定的管线为图形渲染管线
  vk::vkCmdBindPipeline(cmd, VK_PIPELINE_BIND_POINT_GRAPHICS, pipeline);  // 将当前使用的命令缓冲与指定管线绑定
  vk::vkCmdBindDescriptorSets(                                            // 将命令缓冲、管线布局、描述集绑定
      cmd, VK_PIPELINE_BIND_POINT_GRAPHICS, pipelineLayout, 0, 1, desSetPointer, 0, nullptr);
  const VkDeviceSize offsetsVertex[1] = {0};                              // 顶点数据偏移量数组
  vk::vkCmdBindVertexBuffers(                                             // 将顶点数据与当前使用的命令缓冲绑定
      cmd,                                                                // 当前使用的命令缓冲
      0,                                                                  // 顶点数据缓冲在列表中的首索引
      1,                                                                  // 绑定顶点缓冲的数量
      &(vertexDatabuf),                                                   // 绑定的顶点数据缓冲列表
      offsetsVertex                                                       // 各个顶点数据缓冲的内部偏移量
  );
  vk::vkCmdDraw(cmd, vCount, 1, 0, 0);                                    // 执行绘制
}
