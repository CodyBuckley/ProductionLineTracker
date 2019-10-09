package sample;

/**
 * An Enum called itemType that will items will have a pre-set type. Currently there are 4 types:
 * Audio, Visual, Audio-Mobile, and Visual-Mobile. The program will have an abbreviated code to be
 * stored in the database.
 */
public enum ItemType {
  AUDIO("AU"),
  VISUAL("VI"),
  AUDIO_MOBILE("AM"),
  VISUAL_MOBILE("VM");

  // Enum constant, handles abbreviation
  public final String code;

  // Constructor that utilizes code in Enum
  ItemType(String code) {
    this.code = code;
  }

  public String getCode(){
      return code;
  }
}
