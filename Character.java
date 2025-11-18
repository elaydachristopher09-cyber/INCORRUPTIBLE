import java.util.*;

public abstract class Character {
    // ANSI color codes (matching INCORRUPTIBLE.java)
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String BOLD = "\u001B[1m";
    
    protected String name;
    protected int hp, maxHp;
    protected int stamina, maxStamina;
    protected int regen;
    protected Random random = new Random();
   
   
        private static final double STARTING_STAMINA_RATIO = 0.375; // start at 37.5% of max
        private static final double BASIC_RESTORE_RATIO = 0.20; // basic attack restores 20% of max

    // Basic Stats Constructor
    public Character(String name, int hp, int stamina, int regen) {
        this.name = name;
        this.maxHp = hp;
        this.hp = hp;
        this.maxStamina = stamina;
      
        this.stamina = Math.max(1, (int)Math.round(this.maxStamina * STARTING_STAMINA_RATIO));
        this.regen = regen;
    }
    public int getSkillCost() { 
                            return 20;
    } 
    public int getUltimateCost() { 
                            return 60; 
    }

    

    public void recoverAfterStage() {
        int heal = (int)(maxHp * 0.3);
        hp = Math.min(maxHp, hp + heal);
        stamina = maxStamina;
        System.out.println("Recovered 30% HP and full stamina!");
    }

    //Added Date Unknown - Skills Attack 
    protected void performAttack(Character target, int min, int max, int cost, String skillName) {
        if (stamina < cost) {
            System.out.println(RED + name + " lacks stamina for " + skillName + "!" + RESET);
            return;
        }
        stamina -= cost;
        int dmg = min + random.nextInt(max - min + 1);
        target.takeDamage(dmg);
        System.out.println(GREEN + name + " uses " + skillName + " dealing " + dmg + " damage!" + RESET);
    }

    //Added Oct 17 - Basic Attacks restore stamina
    protected void onBasicAttack() {
        int restore = Math.max(1, (int)Math.round(maxStamina * BASIC_RESTORE_RATIO));
        stamina = Math.min(maxStamina, stamina + restore);
        System.out.println(YELLOW + name + " regains " + restore + " stamina from a basic attack." + RESET);
    }

    public abstract void basicAttack(Character target);
    public abstract void skillAttack(Character target);
    public abstract void ultimateAttack(Character target);

    //Added Nov 12 - Combo Attacks
    public int executeCombo(Character target, int minStrikes, int maxStrikes, int minDmg, int maxDmg, int cost, String skillName) {
        if (this.stamina < cost) {
            System.out.println(RED + this.name + " lacks stamina for " + skillName + "!" + RESET);
            return 0;
        }

        // Pay the stamina cost for the entire combo
        this.stamina -= cost;

        int strikes = minStrikes + random.nextInt(Math.max(1, maxStrikes - minStrikes + 1));
        System.out.println(GREEN + this.name + " uses " + skillName + " (" + target.getName() + " gets hit by " + strikes + " times!)" + RESET);

        int total = 0;
        for (int i = 1; i <= strikes; i++) {
            int dmg = minDmg + random.nextInt(Math.max(1, maxDmg - minDmg + 1));
            target.takeDamage(dmg);
            total += dmg;

            String hits;
            switch (i) {
                case 1: hits = "1st"; break;
                case 2: hits = "2nd"; break;
                case 3: hits = "3rd"; break;
                case 4: hits = "4th"; break;
                case 5: hits = "5th"; break;
                case 6: hits = "6th"; break;
                case 7: hits = "7th"; break;
                case 8: hits = "8th"; break;
                default: hits = i + "th"; break;
            }

            System.out.println(YELLOW + hits + " strike deals " +RESET + RED + dmg + " dmg!" + RESET);
        }

        System.out.println(CYAN + BOLD + skillName + " dealt " + RESET + RED +total  +" dmg "+ RESET+ CYAN+BOLD+"in total!" + RESET);
        return total;
    }

    public void takeDamage(int dmg) { hp = Math.max(0, hp - dmg); }
    public boolean isAlive() { return hp > 0; }
    public String getName() { return this.name; }

    /**
     * Sets the character's name. This is used to add suffixes like (You) or (Player 1).
     * @param name The new name for the character.
     */
    public void setName(String name) { 
        this.name = name; 
    }
    public int getHealth() { 
        return this.hp; 
    }
    public int getMaxHealth() { 
        return this.maxHp; 
    }
    public int getStamina() { 
        return this.stamina; 
    }
    public int getMaxStamina() { 
        return this.maxStamina; 
    }
    public void setHealth(int hp) {
    this.hp = Math.max(0, Math.min(hp, this.maxHp));
        }

    public void setStamina(int stamina) {
    this.stamina = Math.max(0, Math.min(stamina, this.maxStamina));
    }
    // === Heal function ===
    public void heal(int amount) {
    this.hp = Math.min(this.hp + amount, this.maxHp);
    }
}
