/* @formatter:off
 *
 * Dave Rosenberg
 * Comp 2000 - Data Structures
 * Lab: Unordered List App - Grocery Bagger
 * Fall, 2024
 *
 * Usage restrictions:
 *
 * You may use this code for exploration, experimentation, and furthering your
 * learning for this course. You may not use this code for any other
 * assignments, in my course or elsewhere, without explicit permission, in
 * advance, from myself (and the instructor of any other course).
 *
 * Further, you may not post (including in a public repository such as on github)
 * nor otherwise share this code with anyone other than current students in my
 * sections of this course. Violation of these usage restrictions will be considered
 * a violation of the Wentworth Institute of Technology Academic Honesty Policy.
 *
 * Do not remove this notice.
 *
 * @formatter:on
 */


package edu.wit.scds.ds.list.unordered.app ;

import java.io.File ;
import java.io.FileNotFoundException ;
import java.util.Objects ;
import java.util.Scanner ;

/**
 * Representation of an item purchased at a grocery store.
 *
 * @author David M Rosenberg
 *
 * @version 1.0 2017-05-23 Initial implementation
 * @version 1.1 2020-02-04
 *     <ul>
 *     <li>clean up formatting
 *     <li>complete documentation
 *     </ul>
 * @version 1.2 2024-10-11
 *     <ul>
 *     <li>add complementary attributes for size, weight, and firmness
 *     <li>enhance comments/documentation to make it easier for students to use
 *     </ul>
 */
public class GroceryItem implements Comparable<GroceryItem>
    {
    /*
     * symbolic constants
     */

    // none


    /*
     * static variables
     */

    // counter to assign each instance a unique id
    private static int itemCount = 0 ;


    /*
     * data fields - a grocery item's attributes can't change so this is an immutable class where
     * all data fields are public, eliminating the need for getter methods
     */

    /**
     * unique id for this instance - enables us to distinguish otherwise identical grocery items
     * from each other
     */
    public final int itemId ;

    // these fields record configured state

    /** item name */
    public final String name ;

    // these fields are enums which are immutable
    /** item size in units */
    public final GroceryItemSize size ;
    /** item weight in units */
    public final GroceryItemWeight weight ;
    /** item firmness */
    public final GroceryItemFirmness firmness ;

    /** flag: item is breakable */
    public final boolean isBreakable ;
    /** flag: item is rigid */
    public final boolean isRigid ;
    /** flag: item is perishable */
    public final boolean isPerishable ;

    // these fields record calculated state

    /** flag: item's weight is a maximum of light */
    public final boolean isLight ;
    /** flag: item's weight is a minimum of heavy */
    public final boolean isHeavy ;
    
    /** flag: item's size is a maximum of small */
    public final boolean isSmall ;
    /** flag: item's size is a minimum of large */
    public final boolean isLarge ;
    
    /** flag: item's firmness is a maximum of soft */
    public final boolean isSoft ;
    /** flag: item's firmness is a minimum of hard */
    public final boolean isHard ;


    /*
     * constructors
     */


    /**
     * configure a grocery item in accordance with the provided specifications
     *
     * @param groceryItemSpecifications
     *     text description of item including its name and attributes
     */
    public GroceryItem( final String groceryItemSpecifications )
        {
        // assign next unique id
        this.itemId = ++itemCount ;

        // break the description into its component parts (tab delimited)
        final String[] itemAttributeDescriptions = groceryItemSpecifications.split( "\t" ) ;

        // set the item's configured attributes accordingly
        this.name = itemAttributeDescriptions[ GroceryItemFieldIds.ITEM_NAME.ordinal() ] ;

        this.size = GroceryItemSize.interpretDescription( itemAttributeDescriptions[ GroceryItemFieldIds.SIZE.ordinal() ] ) ;
        this.weight = GroceryItemWeight.interpretDescription( itemAttributeDescriptions[ GroceryItemFieldIds.WEIGHT.ordinal() ] ) ;
        this.firmness = GroceryItemFirmness.interpretDescription( itemAttributeDescriptions[ GroceryItemFieldIds.FIRMNESS.ordinal() ] ) ;

        this.isRigid = interpretRigidity( itemAttributeDescriptions[ GroceryItemFieldIds.RIGIDITY.ordinal() ] ) ;
        this.isBreakable = interpretBreakability( itemAttributeDescriptions[ GroceryItemFieldIds.BREAKABLE.ordinal() ] ) ;
        this.isPerishable = interpretPerishability( itemAttributeDescriptions[ GroceryItemFieldIds.PERISHABLE.ordinal() ] ) ;

        // set the item's calculated attributes
        this.isLight = this.weight.compareTo( GroceryItemWeight.LIGHT ) <= 0 ;
        this.isHeavy = this.weight.compareTo( GroceryItemWeight.HEAVY ) >= 0 ;
        
        this.isSmall = this.size.compareTo( GroceryItemSize.SMALL ) <= 0 ;
        this.isLarge = this.size.compareTo( GroceryItemSize.LARGE ) >= 0 ;
        
        this.isSoft = this.firmness.compareTo( GroceryItemFirmness.SOFT ) <= 0 ;
        this.isHard = this.firmness.compareTo( GroceryItemFirmness.HARD ) >= 0 ;

        }   // end 1-arg constructor


    /*
     * API methods
     */


    @Override
    public int compareTo( final GroceryItem otherItem )
        {
        // first, check the names
        int comparison = this.name.compareToIgnoreCase( otherItem.name ) ;

        // if the names are the same, check the sizes
        if ( comparison == 0 )
            {
            comparison = this.size.compareTo( otherItem.size ) ;
            }

        // if the names and sizes are the same, check the weights
        if ( comparison == 0 )
            {
            comparison = this.weight.compareTo( otherItem.weight ) ;
            }

        return comparison ;

        }   // end compareTo()


    @Override
    public boolean equals( final Object otherObject )
        {

        if ( otherObject instanceof final GroceryItem otherItem )
            {
            // we have an instance and it's a GroceryItem

            // we'll consider the items the same if their names, sizes, and weights are the same
            // this must be consistent consistent with compareTo()'s 0 value
            return ( this.name.equalsIgnoreCase( otherItem.name ) ) &&
                   ( this.size == otherItem.size ) &&       // enum instances are unique so == works
                   ( this.weight == otherItem.weight ) ;    // enum instances are unique so == works
            }

        // we don't have another GroceryItem to compare to - can't be equal
        return false ;

        }   // end equals()


    @Override
    public int hashCode()
        {
        return Objects.hash( this.name, this.size, this.weight ) ;

        }   // end equals()


    @Override
    public String toString()
        {
        // select info for production
        return this.name ;

        }   // end toString()


    /**
     * returns all state info for debugging
     * 
     * @return formatted dump of the state
     */
    public String toStringDebug()
        {
        // full info for testing/debugging
        return String.format( """
                              [item # %,2d] %s:
                                size: %d (is small: %b; is large: %b);
                                weight: %d (is light: %b; is heavy: %b);
                                firmness: %s (is soft: %b; is hard: %b);
                                rigid: %b; \
                                breakable: %b; \
                                perishable: %b
                              """,
                              this.itemId, this.name,
                              this.size.sizeValue, this.isSmall, this.isLarge,
                              this.weight.weightValue, this.isLight, this.isHeavy,
                              this.firmness.displayName, this.isSoft, this.isHard,
                              this.isRigid,
                              this.isBreakable,
                              this.isPerishable ) ;
        
        }   // end toStringDebug()


    /*
     * private utility methods
     */


    /**
     * Parse a text description of breakability
     *
     * @param breakabilityDescription
     *     a name to parse
     *
     * @return {@code true} if the description matches "true", {@code false} otherwise
     */
    private static boolean interpretBreakability( final String breakabilityDescription )
        {
        // rudimentary strategy: straight case-insensitive text comparison
        return Boolean.parseBoolean( breakabilityDescription ) ;

        }   // end interpretBreakability()


    /**
     * Parse a text description of rigidity
     *
     * @param rigidityDescription
     *     a name to parse
     *
     * @return {@code true} if the description matches "rigid", {@code false} otherwise
     */
    private static boolean interpretRigidity( final String rigidityDescription )
        {
        // rudimentary strategy: straight case-insensitive text comparison
        return rigidityDescription.equalsIgnoreCase( "rigid" ) ;

        }   // end interpretRigidity()


    /**
     * Parse a text description of perishability
     *
     * @param perishabilityDescription
     *     a name to parse
     *
     * @return {@code true} if the description matches "true", {@code false} otherwise
     */
    private static boolean interpretPerishability( final String perishabilityDescription )
        {
        // rudimentary strategy: straight case-insensitive text comparison
        return Boolean.parseBoolean( perishabilityDescription ) ;

        }   // end interpretPerishability()


    /*
     * for testing/debugging
     */


    /**
     * Display contents of groceries.txt in raw and parsed forms
     *
     * @param args
     *     -unused-
     */
    public static void main( final String[] args )
        {

        // get access to groceries.txt
        try ( Scanner groceryList = new Scanner( new File( "./data/conveyor-belt.data" ) ) )
            {
            // display the first line/column headings - don't otherwise process it
            System.out.println( groceryList.nextLine() + '\n' ) ;

            String itemFromCartDescription ;
            GroceryItem groceryItem ;

            // process all entries in the file
            while ( groceryList.hasNextLine() )
                {
                // get the next item
                itemFromCartDescription = groceryList.nextLine() ;
                System.out.println( itemFromCartDescription ) ;

                // instantiating a GroceryItem parses the description
                groceryItem = new GroceryItem( itemFromCartDescription ) ;
                System.out.println( groceryItem ) ;
                System.out.println( groceryItem.toStringDebug() ) ;
                }   // end while()

            }
        catch ( final FileNotFoundException e )
            {
            System.out.printf( "Unable to open data file%n\t%s%n", e.getMessage() ) ;
            }   // end try/catch

        }   // end main()


    /*
     * private utility constructs
     */


    /**
     * the order of attributes in the data file
     * <p>
     * corresponding indices into the item attribute descriptions array - the ordinal() for each
     * instance is its position (0-based) in the enum
     */
    private enum GroceryItemFieldIds
        {
        // each ordinal is the index into the array from split()ting the item description line
        ITEM_NAME,
        SIZE,
        WEIGHT,
        FIRMNESS,
        RIGIDITY,
        BREAKABLE,
        PERISHABLE;

        }   // end enum GroceryItemFieldIds

    }   // end class GroceryItem