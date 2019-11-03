package io.github.daltonsenseman;

/**
 * Interface of the specs all screens must provide.
 *
 * @author Dalton Senseman
 * @since 0.2
 */
@SuppressWarnings("unused")
interface ScreenSpec {

  String getResolution();

  int getRefreshRate();

  int getResponseTime();

}
