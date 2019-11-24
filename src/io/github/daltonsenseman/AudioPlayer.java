package io.github.daltonsenseman;

/**
 * This class contains many methods that are used to display information about AudioPlayers (Such as
 * Formats and action events) this class implements the MultimediaControl which holds the action
 * events methods (Play, Stop, Precious, and Next).
 *
 * @author Dalton Senseman
 * @since 0.2
 */
@SuppressWarnings("unused")
public class AudioPlayer extends Product implements MultimediaControl {

  private final String supportedAudioFormats;
  private final String supportedPlaylistFormats;

  /**
   * Constructor that creates the AudioPlayer with supplied data, the constructor calls super
   * constructor from @code product passing up the values and affixing type param to (AUDIO), the
   * constructor then sets the fields.
   *
   * @param name                     the item name of the AudioPlayer
   * @param manufacturer             name of the manufacturer of the AudioPlayer
   * @param supportedAudioFormats    format of Audio (mp3, wav. etc..)
   * @param supportedPlaylistFormats format of steaming media audio (M3U, ASX, etc...)
   */
  public AudioPlayer(String name, String manufacturer, String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super(name, manufacturer, "AUDIO");
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  /**
   * This method prints to the console that the device is currently playing.
   */
  public void play() {
    System.out.println("Playing");
  }

  /**
   * This method prints to the console that the device is stopping.
   */
  public void stop() {
    System.out.println("Stopping");
  }

  /**
   * This method prints to the console that the device is moving to the precious song.
   */
  public void previous() {
    System.out.println("Previous");
  }

  /**
   * This method prints to the console that the device is moving to the next song in its list.
   */
  public void next() {
    System.out.println("Next");
  }

  /**
   * This method overrides the toString method from the Object class and allows the user to grab the
   * current values of the fields of AudioClass as a String.
   *
   * @return A 2 line String the first showing AudioFormats and the second line PlaylistFormats.
   */
  public String toString() {
    return super.toString() + "Supported Audio Formats: " + supportedAudioFormats + "\n"
        + "Supported Playlist Formats: " + supportedPlaylistFormats;
  }
}
