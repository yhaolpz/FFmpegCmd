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

add_library(libffmpeg SHARED IMPORTED)
set_target_properties(libffmpeg PROPERTIES IMPORTED_LOCATION
        ${ffmpeg_output_shared_lib}/libffmpeg.so)

set(link_lib
        ${link_lib}
        libffmpeg
        )

#以下为固定语法，无需修改
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${lib_output_dir})
include_directories(${include_dir})
set(CMAKE_C_FLAGS_DEBUG "${cmake_c_flag_debug} -s")
set(CMAKE_CXX_FLAGS_DEBUG "${cmake_cxx_flag_debug} -s")
add_library(cmd-ffmpeg SHARED ${source_files})
target_link_libraries(cmd-ffmpeg ${link_lib})