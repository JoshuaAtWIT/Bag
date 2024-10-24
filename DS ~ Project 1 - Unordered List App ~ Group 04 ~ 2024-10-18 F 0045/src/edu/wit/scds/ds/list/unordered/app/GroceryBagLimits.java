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


/**
 * Limits for GroceryBag contents
 *
 * @author David M Rosenberg
 *
 * @version 1.0 2024-02-01 Initial implementation
 * @version 1.1 2024-10-06 modifications for fall 2024 semester
 */
public class GroceryBagLimits
    {

    /** maximum number of GroceryItems a GroceryBag can hold */
    public final static int GROCERY_BAG_MAX_ITEM_COUNT = 15 ;

    /** maximum volume (cumulative sizes) of contained GroceryItems a GroceryBag can hold */
    public final static int GROCERY_BAG_MAX_VOLUME = 18 ;

    /** maximum total weight (cumulative weight) of contained GroceryItems a GroceryBag can hold */
    public final static int GROCERY_BAG_MAX_WEIGHT = 35 ;

    }   // end class GroceryBagLimits