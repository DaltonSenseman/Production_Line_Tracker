package io.github.daltonsenseman;

/**
 * The interface for the action methods Play, Stop, Previous, and Next to be furnished by classes
 * implementing the interface. This class lays out that all Objects must have these features.
 *
 * @author Dalton Senseman
 * @since 0.2
 */
@SuppressWarnings("unused")
interface MultimediaControl {

  void play();

  void stop();

  void previous();

  void next();

}
