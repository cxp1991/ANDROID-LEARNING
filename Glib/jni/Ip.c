/ * File IP.C * /
# include
# include
# include
# include "ip.h"
static gchar * get_addr ( )
{
    char url [ 128 ]  =  { 0 } ;

    g_type_init ( ) ;
    GSocketClient * Client = g_socket_client_new ( ) ;
    snprintf ( url ,  sizeof ( url ) ,  "% s:% s% s" ,  "HTTP" ,  "/ /" ,  "cip.cc" ) ;
    GSocketConnection * c = g_socket_client_connect_to_uri (
        Client , url ,  80 , NULL , NULL ) ;
    if  ( c )  {
        GSocket * socket = NULL ;
        g_object_get ( c ,  "socket" ,  & ; socket , NULL ) ;
        if  ( socket )  {
            char buf [ 2048 ]  =  { 0 } ;
            char  * str = g_malloc0 ( 1024 ) ;
            char  * P ,  * q ;
            char  * send =  "GET / HTTP/1.1 \ r \ N User-Agent: curl/7.29.0 \ r \ N Host: cip.cc \ r \ N Accept: * / * \ r \ N \ r \ N " ;
            g_socket_send ( socket , send , strlen ( send )  +  1 , NULL , NULL ) ;
            g_socket_receive ( socket , buf ,  sizeof ( buf ) , NULL , NULL ) ;
            P = strstr ( buf ,  "IP" ) ;
            q = strstr ( P ,  " \ N " ) ;
            if  ( P & ; & ; q )  {
                char ip [ 32 ]  =  { 0 } ;
                snprintf ( ip , q - P +  2 ,  "% s" , P ) ;
                strcat ( str , ip ) ;
            }

            P = strstr ( buf ,  "address" ) ;
            q = strstr ( P ,  " \ N " ) ;
            if  ( P & ; & ; q )  {
                char addr [ 1024 ]  =  { 0 } ;
                snprintf ( addr , q - P +  2 ,  "% s" , P ) ;
                strcat ( str , addr ) ;
            }
            g_socket_close ( socket , NULL ) ;
            return str ;
        }
    }
    g_object_unref ( Client ) ;
    return NULL ;
}

JNIEXPORT jstring JNICALL Java_org_mathslinux_glib_1demo_GetIPAddr_getAddr
( JNIEnv * je , JClass jc )
{
    char  * addr = get_addr ( ) ;
    return  ( * je ) - & GT ; NewStringUTF ( je , addr ) ;
}
