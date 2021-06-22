package org.springframework.safe.utils.enums;

/**
 * @author Sir.D
 */
public enum SystemOS {

    // linux 操作系统
    LINUX ( "LINUX" ),
    // macos 操作系统
    MACOS ( "MAC OS X" ),
    // windows 操作系统
    WINDOWS ( "WINDOWS" ),
    ;

    private final String code;
    public String code(){ return code; }
    SystemOS(String code ) { this.code = code; }

    public static SystemOS fromCode(String code ) {
        for ( SystemOS source : values() ) {
            if ( source.code.equals(code) ) {
                return source;
            }
        }
        return null;
    }

}
