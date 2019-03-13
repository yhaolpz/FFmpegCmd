cmake_minimum_required(VERSION 3.4.1)

#初步生成的ffmpeg动态库地址
set(ffmpeg_output_shared_lib
        ${CMAKE_SOURCE_DIR}/libs/armeabi-v7a/
        CACHE INTERNAL "ffmpeg_output_shared_lib_dir"
        )
#初步生成的ffmpeg头文件
set(ffmpeg_output_include_dir
        ${CMAKE_SOURCE_DIR}/src/cpp/include
        CACHE INTERNAL "ffmpeg_output_include_dir"
        )

add_subdirectory(${CMAKE_SOURCE_DIR}/src/main/cpp)