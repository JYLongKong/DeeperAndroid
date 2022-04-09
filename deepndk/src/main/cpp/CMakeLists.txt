cmake_minimum_required(VERSION 3.10.2)

set(FFMPEG ${CMAKE_SOURCE_DIR}/ffmpeg)
set(RTMP ${CMAKE_SOURCE_DIR}/rtmp)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L${FFMPEG}/libs/${CMAKE_ANDROID_ARCH_ABI}")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L${RTMP}/libs/${CMAKE_ANDROID_ARCH_ABI}")

include_directories(${FFMPEG}/include)

file(GLOB deepplayer_src deepplayer/*.cpp)

add_library(
        deepplayer
        SHARED
        ${deepplayer_src}
)

target_link_libraries(
        deepplayer

        -Wl,--start-group   # Tip8: Ignore sequence of linked libraries in CMake
        avcodec avfilter avformat avutil swresample swscale
        -Wl,--end-group     # ignore sequence of linked libraries in CMake

        log
        z           # libz.so: support for FFmpeg
        rtmp
        android     # for rendering surface
        OpenSLES    # for playing sound
)
