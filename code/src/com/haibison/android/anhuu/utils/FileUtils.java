/*
 *    Copyright (c) 2012 Hai Bison
 *
 *    See the file LICENSE at the root directory of this project for copying
 *    permission.
 */

package com.haibison.android.anhuu.utils;

import java.util.regex.Pattern;

import android.util.SparseArray;

import com.haibison.android.anhuu.R;
import com.haibison.android.anhuu.providers.basefile.BaseFileContract.BaseFile;

/**
 * Utilities for files.
 * 
 * @author Hai Bison
 * @since v4.3 beta
 */
public class FileUtils {

    /**
     * Map of the pattern for file types corresponding to resource IDs for
     * icons.
     */
    private static final SparseArray<Pattern> MAP_FILE_ICONS = new SparseArray<Pattern>();

    static {
        MAP_FILE_ICONS.put(R.drawable.anhuu_f5be488d_file_audio,
                Pattern.compile(MimeTypes.REGEX_FILE_TYPE_AUDIOS));
        MAP_FILE_ICONS.put(R.drawable.anhuu_f5be488d_file_video,
                Pattern.compile(MimeTypes.REGEX_FILE_TYPE_VIDEOS));
        MAP_FILE_ICONS.put(R.drawable.anhuu_f5be488d_file_image,
                Pattern.compile(MimeTypes.REGEX_FILE_TYPE_IMAGES));
        MAP_FILE_ICONS.put(R.drawable.anhuu_f5be488d_file_plain_text,
                Pattern.compile(MimeTypes.REGEX_FILE_TYPE_PLAIN_TEXTS));

        /*
         * APK files are counted before compressed files.
         */
        MAP_FILE_ICONS.put(R.drawable.anhuu_f5be488d_file_apk,
                Pattern.compile(MimeTypes.REGEX_FILE_TYPE_APKS));
        MAP_FILE_ICONS.put(R.drawable.anhuu_f5be488d_file_compressed,
                Pattern.compile(MimeTypes.REGEX_FILE_TYPE_COMPRESSED));
    }

    /**
     * Gets resource icon based on file type and name.
     * 
     * @param fileType
     *            the file type, can be one of
     *            {@link BaseFile#FILE_TYPE_DIRECTORY},
     *            {@link BaseFile#FILE_TYPE_FILE},
     *            {@link BaseFile#FILE_TYPE_UNKNOWN}.
     * @param fileName
     *            the file name.
     * @return the resource icon ID.
     */
    public static int getResIcon(int fileType, String fileName) {
        switch (fileType) {
        case BaseFile.FILE_TYPE_DIRECTORY: {
            return R.drawable.anhuu_f5be488d_folder;
        }// FILE_TYPE_DIRECTORY

        case BaseFile.FILE_TYPE_FILE: {
            for (int i = 0; i < MAP_FILE_ICONS.size(); i++)
                if (MAP_FILE_ICONS.valueAt(i).matcher(fileName).find())
                    return MAP_FILE_ICONS.keyAt(i);

            return R.drawable.anhuu_f5be488d_file;
        }// FILE_TYPE_FILE

        default:
            return android.R.drawable.ic_delete;
        }
    }// getResIcon()

    /**
     * Checks whether the filename given is valid or not.
     * <p/>
     * See <a href="http://en.wikipedia.org/wiki/Filename">wiki</a> for more
     * information.
     * 
     * @param name
     *            name of the file
     * @return {@code true} if the {@code name} is valid, and vice versa (if it
     *         contains invalid characters or it is {@code null}/ empty)
     */
    public static boolean isFilenameValid(String name) {
        return name != null && name.trim().matches("[^\\\\/?%*:|\"<>]+");
    }// isFilenameValid()

}