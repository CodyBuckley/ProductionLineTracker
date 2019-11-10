package production.line;

/**
 * We require a concrete class that will allow us to capture the details of an audio player. Create
 * a class called AudioPlayer that is a subclass of Product and implements the MultimediaControl
 * interface.
 */
public class AudioPlayer extends Product implements MultimediaControl {

  private String supportedAudioFormats;
  private String supportedPlaylistFormats;

  /**
   * Create a constructor that will take in 4 parameters: name, manufacturer, supportedAudioFormats,
   * and supportedPlaylistFormats.
   *
   * @param name the name of the product
   * @param manufacturer the name of the manufacturer
   * @param supportedAudioFormats the name of supported audio players
   * @param supportedPlaylistFormats the name of supported playlist for the audio players
   */
  public AudioPlayer(
      String name,
      String manufacturer,
      String supportedAudioFormats,
      String supportedPlaylistFormats) {

    // Call the parent constructor and default to Audio for type
    super(name, manufacturer, ItemType.AUDIO);

    // Initialize the two fields inside the constructor
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  // Basic media control "Play" is defaulted to print "Playing"
  public void play() {
    System.out.println("Playing");
  }

  // Basic media control "Stop" is defaulted to print "Stopping"
  public void stop() {
    System.out.println("Stopping");
  }

  // Basic media control "Next" is defaulted to print "Next"
  public void next() {
    System.out.println("Next");
  }

  // Basic media control "Previous" is defaulted to print "Previous"
  public void previous() {
    System.out.println("Previous");
  }

  // Overridden method called toString to return the toString from the parent class Product and adds
  // the supported Audio Formats and supported Playlist Formats.
  @Override
  public String toString() {
    return super.toString()
        + "\nSupported Audio Formats: "
        + supportedAudioFormats
        + "\nSupported Playlist Formats: "
        + supportedPlaylistFormats;
  }
}
