package net.velion.kingdoms_arena.arena.entity.inventory;

public enum InventorySlot
{
    ROW1_SLOT1(0),
    ROW1_SLOT2(1),
    ROW1_SLOT3(2),
    ROW1_SLOT4(3),
    ROW1_SLOT5(4),
    ROW1_SLOT6(5),
    ROW1_SLOT7(6),
    ROW1_SLOT8(7),
    ROW1_SLOT9(8),
    ROW2_SLOT1(9),
    ROW2_SLOT2(10),
    ROW2_SLOT3(11),
    ROW2_SLOT4(12),
    ROW2_SLOT5(13),
    ROW2_SLOT6(14),
    ROW2_SLOT7(15),
    ROW2_SLOT8(16),
    ROW2_SLOT9(17),
    ROW3_SLOT1(18),
    ROW3_SLOT2(19),
    ROW3_SLOT3(20),
    ROW3_SLOT4(21),
    ROW3_SLOT5(22),
    ROW3_SLOT6(23),
    ROW3_SLOT7(24),
    ROW3_SLOT8(25),
    ROW3_SLOT9(26),
    ROW4_SLOT1(27),
    ROW4_SLOT2(28),
    ROW4_SLOT3(29),
    ROW4_SLOT4(30),
    ROW4_SLOT5(31),
    ROW4_SLOT6(32),
    ROW4_SLOT7(33),
    ROW4_SLOT8(34),
    ROW4_SLOT9(35),
    BOOTS(36),
    PANTS(37),
    LEGGINGS(37),
    CHEST(38),
    HEAD(39),
    SHIELD(40);

    private int val;

    InventorySlot(int val)
    {
        this.val = val;
    }

    public int get()
    {
        return val;
    }
}
