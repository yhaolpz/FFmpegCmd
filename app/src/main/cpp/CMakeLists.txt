cmake_minimum_required(VERSION 3.4.1)

#获取当前目录
set(pwd_dir  ${CMAKE_SOURCE_DIR}/src/main/cpp)

#设置头文件
set(include_dir
        ${pwd_dir}
        ${pwd_dir}/../../../../../FFmpeg
        ${ffmpeg_output_include_dir}
        )

#将若干库链接到目标库文件
set(link_lib
        log
        android
        m
        )

#设置编译选项
set(cmake_c_flag_debug

        )
set(cmake_cxx_flag_debug

        )
#设置要编译的源文件
set(source_files
        ${pwd_dir}/ffmpeg-jni.cpp
        ${pwd_dir}/cmdutils.c
        ${pwd_dir}/ffmpeg.c
        ${pwd_dir}/ffmpeg_opt.c
        ${pwd_dir}/ffmpeg_filter.c
        )

#其他配置
add_library(libavcodec SHARED IMPORTED)
set_target_properties(libavcodec PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_output_shared_lib}/libavcodec-57.so)

add_library(libavdevice SHARED IMPORTED)
set_target_properties(libavdevice PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_output_shared_lib}/libavdevice-57.so)

add_library(libavfilter SHARED IMPORTED)
set_target_properties(libavfilter PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_output_shared_lib}/libavfilter-6.so)

add_library(libavformat SHARED IMPORTED)
set_target_properties(libavformat PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_output_shared_lib}/libavformat-57.so)

add_library(libavutil SHARED IMPORTED)
set_target_properties(libavutil PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_output_shared_lib}/libavutil-55.so)

add_library(libswresample SHARED IMPORTED)
set_target_properties(libswresample PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_output_shared_lib}/libswresample-2.so)

add_library(libswscale SHARED IMPORTED)
set_target_properties(libswscale PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_output_shared_lib}/libswscale-4.so)


set(link_lib
        ${link_lib}
        libavcodec
        libavdevice
        libavfilter
        libavformat
        libavutil
        libswresample
        libswscale
        )

#以下为固定语法，无需修改
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${lib_output_dir})
include_directories(${include_dir})
set(CMAKE_C_FLAGS_DEBUG "${cmake_c_flag_debug} -s")
set(CMAKE_CXX_FLAGS_DEBUG "${cmake_cxx_flag_debug} -s")
add_library(cmd-ffmpeg SHARED ${source_files})
target_link_libraries(cmd-ffmpeg ${link_lib})