package pp.model;

import pp.Utils;
import pp.model.xml.CGlad;
import pp.model.xml.Cgl;
import pp.model.xml.Cxml;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scheme {
    public static final Scheme SLIV00100 = new Scheme(new Slot(GladCategory.SLIV, 0, 0, 100, 1, 1));
    public static final Scheme KACH_ACTIVE = new Scheme(
            Slot.create(GladCategory.CHOSEN, 100, 0, 100, 5, 3),
            Slot.create(GladCategory.FRAK, 100, 0, 100, 4, 3));
            //Slot.create(GladCategory.RET, 100, 100, 100, 3, 3));
    public static final Scheme KACH_HORSE = new Scheme(
            Slot.create(GladCategory.CHOSEN, 100, 100, 100, 5, 3),
            Slot.create(GladCategory.CHOSEN2, 100, 100, 100, 1, 3));
    public static final Scheme KACH_PASSIVE = new Scheme(
            Slot.create(GladCategory.MAIN_TYPE_VELHOP, 0, 0, 0, 5, 3),
            Slot.create(GladCategory.MAIN_TYPE_HOP, 0, 0, 0, 4, 3),
            Slot.create(GladCategory.MAIN_TYPE_NOVELHOP, 0, 0, 0, 3, 3),
            Slot.create(GladCategory.MAIN_TYPE_NOVELHOP, 0, 0, 0, 2, 3),
            Slot.create(GladCategory.MAIN_TYPE_NOVELHOP, 0, 0, 0, 1, 3));
    public static final Scheme KACH_HORSE_PASSIVE = new Scheme(
            Slot.create(GladCategory.MAIN_TYPE_VELHOP, 0, 0, 100, 5, 1),
            Slot.create(GladCategory.MAIN_TYPE_HOP, 0, 0, 100, 5, 2),
            Slot.create(GladCategory.MAIN_TYPE_NOVELHOP, 0, 0, 100, 5, 3),
            Slot.create(GladCategory.MAIN_TYPE_NOVELHOP, 0, 0, 100, 5, 4),
            Slot.create(GladCategory.MAIN_TYPE_NOVELHOP, 0, 0, 100, 5, 5));

    /*
        MAIN_1_1_1(Slot.create(GladCategory.DIM, 73, 25, 15, 1, 1)),
        MAIN_1_1_2(Slot.create(GladCategory.DIM, 40, 40, 15, 1, 1)),
        MAIN_1_1_3(Slot.create(GladCategory.DIM, 0, 35, 0, 1, 1)),
        MAIN_1_2_1(Slot.create(GladCategory.FRAK, 73, 67, 25, 1, 1)),
        MAIN_1_2_2(Slot.create(GladCategory.FRAK, 0, 65, 0, 1, 1)),
        MAIN_1_2_3(Slot.create(GladCategory.FRAK, 0, 65, 20, 1, 1)),
        MAIN_1_2_4(Slot.create(GladCategory.FRAK, 90, 90, 90, 1, 1)),
        MAIN_1_2_5(Slot.create(GladCategory.FRAK, 100, 67, 25, 1, 1)),
        MAIN_1_3_1(Slot.create(GladCategory.HOP, 30, 60, 100, 1, 1)),
        MAIN_1_3_2(Slot.create(GladCategory.HOP, 10, 40, 100, 1, 1)),
        MAIN_1_4_1(Slot.create(GladCategory.SEC, 99, 50, 0, 1, 1)),
        MAIN_1_5_1(Slot.create(GladCategory.RET, 100, 80, 100, 1, 1)),
        MAIN_1_6_1(Slot.create(GladCategory.MUR, 15, 80, 30, 1, 1)),
        MAIN_1_7_1(Slot.create(GladCategory.VEL, 100, 30, 50, 1, 1)),

        MAIN_2_1(
                Slot.create(GladCategory.DUELIST, 100, -1, -1, 1, 2),
                Slot.create(GladCategory.DUELIST, 100, -1, -1, 1, 1)
        ),
        MAIN_2_2(
                Slot.create(GladCategory.HOP, 70, 20, 100, 2, 2),
                Slot.create(GladCategory.FRAK, 100, 80, 0, 1, 2)
        ),
        MAIN_2_3(
                Slot.create(GladCategory.MUR, 70, 70, 70, 2, 2),
                Slot.create(GladCategory.DIM, 100, 30, 30, 1, 2)
        ),
        MAIN_2_4(
                Slot.create(GladCategory.MUR, 70, 30, 30, 2, 2),
                Slot.create(GladCategory.FRAK, 100, 70, 70, 1, 2)
        ),
        MAIN_2_5(
                Slot.create(GladCategory.SEC, 70, 80, 80, 2, 2),
                Slot.create(GladCategory.DIM, 100, 20, 20, 1, 2)
        ),
        MAIN_2_6(
                Slot.create(GladCategory.SEC, 70, 20, 20, 2, 2),
                Slot.create(GladCategory.FRAK, 100, 80, 80, 1, 2)
        ),
        MAIN_2_7(
                Slot.create(GladCategory.DIM, 70, 20, 20, 2, 2),
                Slot.create(GladCategory.FRAK, 100, 80, 80, 1, 2)
        ),


        MAIN_3_1(
                Group.create(
                        Slot.create(GladCategory.SEC, 0, 0, 0, 3, 2),
                        Slot.create(GladCategory.DIM, 97, 0, 0, 2, 2),
                        Slot.create(GladCategory.FRAK, 97, 100, 100, 1, 2))
        ),

        MAIN_3_2(
                Group.create(
                        Slot.create(GladCategory.DIM, 100, 0, 0, 5, 4),
                        Slot.create(GladCategory.SEC, 100, 20, 20, 5, 3),
                        Slot.create(GladCategory.FRAK, 100, 67, 25, 5, 2))
        ),
        MAIN_3_3(
                Group.create(
                        Slot.create(GladCategory.SEC, 100, 20, 0, 5, 3),
                        Slot.create(GladCategory.RET, 100, 40, 20, 5, 4),
                        Slot.create(GladCategory.VEL, 70, 100, 50, 1, 5))
        ),
        MAIN_3_4(
                Group.create(
                        Slot.create(GladCategory.SEC, 100, 30, 0, 5, 3),
                        Slot.create(GladCategory.DIM, 100, 15, 0, 5, 2),
                        Slot.create(GladCategory.VEL, 70, 100, 50, 1, 1))
        ),
        MAIN_3_5(
                Group.create(
                        Slot.create(GladCategory.MUR, 99, 50, 50, 5, 3),
                        Slot.create(GladCategory.HOP, 99, 50, 100, 5, 2),
                        Slot.create(GladCategory.DAMAGER, 99, 50, 0, 5, 1))
        ),
        MAIN_3_6(
                Group.create(
                        Slot.create(GladCategory.SEC, 99, 0, 0, 5, 3),
                        Slot.create(GladCategory.HOP, 99, 50, 100, 5, 2),
                        Slot.create(GladCategory.DAMAGER, 99, 50, 0, 5, 1))
        ),
        MAIN_4_1(
                Group.create(
                        Slot.create(GladCategory.SEC, 100, 25, 0, 5, 3),
                        Slot.create(GladCategory.DIM, 100, 40, 0, 5, 4),
                        Slot.create(GladCategory.RET, 100, 70, 70, 5, 5),
                        Slot.create(GladCategory.VEL, 100, 100, 0, 4, 5))
        ),
        MAIN_4_2(
                Group.create(
                        Slot.create(GladCategory.SEC, 20, 40, 15, 4, 2),
                        Slot.create(GladCategory.DIM, 95, 20, 0, 2, 2),
                        Slot.create(GladCategory.RET, 100, 70, 70, 1, 1),
                        Slot.create(GladCategory.FRAK, 100, 40, 40, 1, 2))
        ),
        MAIN_4_3(
                Group.create(
                        Slot.create(GladCategory.SEC, 90, 30, 30, 5, 3),
                        Slot.create(GladCategory.DIM, 90, 25, 15, 5, 2),
                        Slot.create(GladCategory.VEL, 100, 100, 50, 3, 3),
                        Slot.create(GladCategory.VEL, 100, 100, 50, 3, 2))
        ),
        MAIN_4_4(
                Group.create(
                        Slot.create(GladCategory.DIM, 100, 0, 0, 5, 2),
                        Slot.create(GladCategory.SEC, 100, 0, 0, 5, 3),
                        Slot.create(GladCategory.FRAK, 100, 0, 0, 5, 4),
                        Slot.create(GladCategory.DIM, 100, 0, 0, 5, 5))
        ),
        MAIN_4_5(
                Group.create(
                        Slot.create(GladCategory.DIM, 100, 50, 50, 5, 2),
                        Slot.create(GladCategory.SEC, 100, 50, 50, 5, 3),
                        Slot.create(GladCategory.FRAK, 100, 50, 50, 5, 4),
                        Slot.create(GladCategory.DIM, 100, 50, 50, 5, 5))
        ),
        MAIN_4_6(
                Group.create(
                        Slot.create(GladCategory.RET, 95, 40, 0, 5, 3),
                        Slot.create(GladCategory.SEC, 95, 40, 40, 5, 2),
                        Slot.create(GladCategory.VEL, 100, 100, 50, 3, 3),
                        Slot.create(GladCategory.VEL, 100, 100, 50, 3, 2))
        ),
        MAIN_5_1(
                Group.create(
                        Slot.create(GladCategory.SEC, 0, 0, 0, 5, 4),
                        Slot.create(GladCategory.HOP, 70, 100, 100, 2, 5),
                        Slot.create(GladCategory.RET, 70, 0, 0, 2, 4),
                        Slot.create(GladCategory.FRAK, 100, 100, 100, 1, 4),
                        Slot.create(GladCategory.DIM, 100, 0, 0, 1, 5))
                        ),

        MAIN_5_2(
                Group.create(
                        Slot.create(GladCategory.SEC, 30, 40, 0, 4, 2),
                        Slot.create(GladCategory.DIM, 95, 20, 15, 2, 1),
                        Slot.create(GladCategory.DIM, 95, 20, 15, 2, 2),
                        Slot.create(GladCategory.RET, 100, 40, 40, 1, 1),
                        Slot.create(GladCategory.FRAK, 100, 75, 75, 1, 2))
                        ),
        MAIN_5_3(
                Group.create(
                        Slot.create(GladCategory.DIM, 100, 0, 0, 5, 1),
                        Slot.create(GladCategory.FRAK, 100, 0, 0, 5, 2),
                        Slot.create(GladCategory.SEC, 100, 0, 0, 5, 3),
                        Slot.create(GladCategory.DIM, 100, 0, 0, 5, 4),
                        Slot.create(GladCategory.FRAK, 100, 0, 0, 5, 5))
                ),
        MAIN_5_4(
                Group.create(
                        Slot.create(GladCategory.DIM, 100, 0, 0, 5, 1),
                        Slot.create(GladCategory.FRAK, 100, 100, 50, 5, 2),
                        Slot.create(GladCategory.SEC, 100, 40, 40, 5, 3),
                        Slot.create(GladCategory.DIM, 100, 0, 0, 5, 4),
                        Slot.create(GladCategory.FRAK, 100, 100, 50, 5, 5))
                ),
        MAIN_5_5(
                Group.create(
                        Slot.create(GladCategory.VEL, 100, 100, 50, 1, 1),
                        Slot.create(GladCategory.DIM, 100, 20, 0, 5, 2),
                        Slot.create(GladCategory.SEC, 100, 40, 40, 5, 3),
                        Slot.create(GladCategory.DIM, 100, 20, 0, 5, 4),
                        Slot.create(GladCategory.VEL, 100, 100, 50, 1, 5))
                ),
        MAIN_5_6(
                Group.create(
                        Slot.create(GladCategory.FRAK, 100, 40, 0, 1, 2),
                        Slot.create(GladCategory.RET, 100, 70, 40, 3, 1),
                        Slot.create(GladCategory.RET, 100, 70, 40, 3, 2),
                        Slot.create(GladCategory.SEC, 0, 100, 20, 4, 3),
                        Slot.create(GladCategory.DIM, 100, 0, 0, 1, 4))
                ),

        MAIN_6_1(
                Group.create(
                        Slot.create(GladCategory.TANK, 100, 50, 50, 1, 3),
                        Slot.create(GladCategory.DAMAGER, 100, 50, 50, 1, 2),
                        Slot.create(GladCategory.DAMAGER, 100, 50, 50, 1, 4),
                        Slot.create(GladCategory.DAMAGER, 100, 50, 50, 1, 1),
                        Slot.create(GladCategory.DAMAGER, 100, 50, 50, 2, 5),
                        Slot.create(GladCategory.RET, 100, 50, 50, 1, 5))
        ),
        MAIN_7_1(
                Group.create(
                        Slot.create(GladCategory.TANK, 100, 50, 50, 1, 3),
                        Slot.create(GladCategory.DAMAGER, 100, 50, 50, 1, 2),
                        Slot.create(GladCategory.DAMAGER, 100, 50, 50, 1, 4),
                        Slot.create(GladCategory.DAMAGER, 100, 50, 50, 2, 1),
                        Slot.create(GladCategory.DAMAGER, 100, 50, 50, 2, 5),
                        Slot.create(GladCategory.RET, 100, 50, 50, 1, 5),
                        Slot.create(GladCategory.RET, 100, 50, 50, 1, 1))
        ),
        SURVIVE_1_1(
                Slot.create(GladCategory.DUELIST, -1, -1, -1, 1, 1)
        ),
        SURVIVE_2_1(
                Slot.create(GladCategory.DUELIST, -1, -1, -1, 1, 1),
                Slot.create(GladCategory.DUELIST, -1, -1, -1, 1, 2)
        ),
        SURVIVE_3_1(
                Slot.create(GladCategory.SEC, 95, 60, 30, 1, 1),
                Slot.create(GladCategory.DIM, 95, 25, 15, 1, 2),
                Slot.create(GladCategory.FRAK, 95, 65, 25, 1, 3)
        ),
        SURVIVE_3_2(
                Slot.create(GladCategory.DIM, 100, 50, 50, 1, 1),
                Slot.create(GladCategory.FRAK, 100, 67, 30, 1, 2),
                Slot.create(GladCategory.DIM, 100, 20, 10, 1, 3)
        ),
        SURVIVE_3_3(
                Slot.create(GladCategory.RET, 73, 70, 35, 1, 1),
                Slot.create(GladCategory.DIM, 95, 40, 15, 1, 2),
                Slot.create(GladCategory.FRAK, 100, 67, 25, 1, 3)
        ),
        SURVIVE_3_4(
                Slot.create(GladCategory.DIM, 99, 0, 0, 1, 1),
                Slot.create(GladCategory.HOP, 10, 40, 100, 1, 2),
                Slot.create(GladCategory.RET, 100, 100, 100, 1, 3)
        ),
        SURVIVE_5_1(
                Slot.create(GladCategory.SEC, 80, 80, 80, 1, 1),
                Slot.create(GladCategory.RET, 95, 40, 40, 1, 2),
                Slot.create(GladCategory.HOP, 20, 40, 100, 1, 3),
                Slot.create(GladCategory.DIM, 100, 50, 50, 1, 4),
                Slot.create(GladCategory.FRAK, 100, 50, 50, 1, 5)
        ),
        SURVIVE_5_2(
                Slot.create(GladCategory.SEC, 90, 60, 30, 1, 1),
                Slot.create(GladCategory.RET, 95, 40, 40, 1, 2),
                Slot.create(GladCategory.MUR, 10, 80, 100, 1, 3),
                Slot.create(GladCategory.DIM, 100, 50, 50, 1, 4),
                Slot.create(GladCategory.FRAK, 100, 50, 50, 1, 5)
        ),
        SURVIVE_5_3(
                Slot.create(GladCategory.RET, 73, 70, 33, 1, 1),
                Slot.create(GladCategory.SEC, 100, 40, 30, 1, 2),
                Slot.create(GladCategory.HOP, 20, 40, 100, 1, 3),
                Slot.create(GladCategory.DIM, 100, 50, 50, 1, 4),
                Slot.create(GladCategory.FRAK, 100, 50, 50, 1, 5)
        ),
        HORSE_1_1(
                Slot.create(GladCategory.DUELIST, -1, -1, -1, 1, 1)
        ),
        HORSE_2_1(
                Slot.create(GladCategory.HORSE, 70, 40, 20, 5, 2),
                Slot.create(GladCategory.HORSE, 80, 57, 20, 2, 2)
        ),
        HORSE_2_2(
                Slot.create(GladCategory.HORSE, 73, 73, 15, 5, 2),
                Slot.create(GladCategory.HORSE, 70, 20, 20, 2, 2)
        ),
        HORSE_2_3(
                Slot.create(GladCategory.HORSE, 75, 40, 10, 5, 2),
                Slot.create(GladCategory.HORSE, 80, 20, 20, 2, 2)
        ),
        HORSE_2_4(
                Slot.create(GladCategory.HORSE, 100, 100, 20, 5, 2),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 2, 2)
        ),
        HORSE_3_1(
                Slot.create(GladCategory.TELEGA, 100, 0, 0, 1, 3),
                Slot.create(GladCategory.HORSE, 73, 67, 23, 5, 2),
                Slot.create(GladCategory.HORSE, 70, 40, 20, 2, 2)
        ),
        HORSE_3_2(
                Slot.create(GladCategory.TELEGA, 100, 0, 0, 1, 3),
                Slot.create(GladCategory.HORSE, 83, 40, 20, 5, 2),
                Slot.create(GladCategory.HORSE, 73, 20, 20, 2, 2)
        ),
        HORSE_3_3(
                Slot.create(GladCategory.TELEGA, 100, 0, 0, 1, 3),
                Slot.create(GladCategory.HORSE, 0, 53, 17, 5, 2),
                Slot.create(GladCategory.HORSE, 73, 37, 20, 2, 2)
        ),
        HORSE_3_4(
                Slot.create(GladCategory.TELEGA, 100, 0, 0, 1, 3),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 5, 2),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 2, 2)
        ),
        HORSE_3_5(
                Slot.create(GladCategory.DIM, 97, 0, 0, 5, 3),
                Slot.create(GladCategory.HORSE, 73, 67, 23, 2, 3),
                Slot.create(GladCategory.HORSE, 70, 40, 20, 1, 3)
        ),
        HORSE_3_6(
                Slot.create(GladCategory.DIM, 90, 40, 0, 5, 3),
                Slot.create(GladCategory.HORSE, 83, 40, 20, 2, 3),
                Slot.create(GladCategory.HORSE, 73, 20, 20, 1, 3)
        ),
        HORSE_3_7(
                Slot.create(GladCategory.DIM, 90, 40, 20, 5, 3),
                Slot.create(GladCategory.HORSE, 0, 53, 17, 2, 3),
                Slot.create(GladCategory.HORSE, 73, 37, 20, 1, 3)
        ),
        HORSE_3_8(
                Slot.create(GladCategory.DIM, 95, 10, 0, 5, 3),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 2, 3),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 1, 3)
        ),
        HORSE_3_9(
                Slot.create(GladCategory.FRAK, 100, 67, 40, 5, 3),
                Slot.create(GladCategory.HORSE, 73, 67, 23, 2, 3),
                Slot.create(GladCategory.HORSE, 70, 40, 20, 1, 3)
        ),
        HORSE_3_10(
                Slot.create(GladCategory.FRAK, 95, 40, 40, 5, 3),
                Slot.create(GladCategory.HORSE, 83, 40, 20, 2, 3),
                Slot.create(GladCategory.HORSE, 73, 20, 20, 1, 3)
        ),
        HORSE_3_11(
                Slot.create(GladCategory.FRAK, 73, 67, 25, 5, 3),
                Slot.create(GladCategory.HORSE, 0, 53, 17, 2, 3),
                Slot.create(GladCategory.HORSE, 73, 37, 20, 1, 3)
        ),
        HORSE_3_12(
                Slot.create(GladCategory.FRAK, 73, 67, 25, 5, 3),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 2, 3),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 1, 3)
        ),
        HORSE_4_1(
                Slot.create(GladCategory.DIM, 90, 40, 0, 5, 3),
                Slot.create(GladCategory.TELEGA, 100, 0, 0, 1, 3),
                Slot.create(GladCategory.HORSE, 73, 67, 23, 3, 3),
                Slot.create(GladCategory.HORSE, 70, 40, 20, 2, 4)
        ),
        HORSE_4_2(
                Slot.create(GladCategory.DIM, 97, 0, 0, 5, 3),
                Slot.create(GladCategory.TELEGA, 100, 0, 0, 1, 3),
                Slot.create(GladCategory.HORSE, 83, 40, 20, 3, 3),
                Slot.create(GladCategory.HORSE, 73, 20, 20, 2, 4)
        ),
        HORSE_4_3(
                Slot.create(GladCategory.DIM, 98, 10, 10, 5, 3),
                Slot.create(GladCategory.TELEGA, 100, 0, 0, 1, 3),
                Slot.create(GladCategory.HORSE, 0, 53, 17, 3, 3),
                Slot.create(GladCategory.HORSE, 73, 37, 20, 2, 4)
        ),
        HORSE_4_4(
                Slot.create(GladCategory.DIM, 65, 50, 20, 5, 3),
                Slot.create(GladCategory.TELEGA, 100, 0, 0, 1, 3),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 3, 3),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 2, 4)
        ),
        HORSE_4_5(
                Slot.create(GladCategory.FRAK, 100, 67, 25, 5, 3),
                Slot.create(GladCategory.TELEGA, 100, 0, 0, 1, 3),
                Slot.create(GladCategory.HORSE, 73, 67, 23, 3, 3),
                Slot.create(GladCategory.HORSE, 70, 40, 20, 2, 4)
        ),
        HORSE_4_6(
                Slot.create(GladCategory.FRAK, 90, 65, 25, 5, 3),
                Slot.create(GladCategory.TELEGA, 100, 0, 0, 1, 3),
                Slot.create(GladCategory.HORSE, 83, 40, 20, 3, 3),
                Slot.create(GladCategory.HORSE, 73, 20, 20, 2, 4)
        ),
        HORSE_4_7(
                Slot.create(GladCategory.FRAK, 97, 27, 20, 5, 3),
                Slot.create(GladCategory.TELEGA, 100, 0, 0, 1, 3),
                Slot.create(GladCategory.HORSE, 0, 53, 17, 3, 3),
                Slot.create(GladCategory.HORSE, 73, 37, 20, 2, 4)
        ),
        HORSE_4_8(
                Slot.create(GladCategory.DIM, 90, 40, 0, 5, 3),
                Slot.create(GladCategory.VEL, 100, 0, 0, 2, 1),
                Slot.create(GladCategory.HORSE, 73, 67, 23, 2, 3),
                Slot.create(GladCategory.HORSE, 70, 40, 20, 1, 3)
        ),
        HORSE_4_9(
                Slot.create(GladCategory.DIM, 97, 0, 0, 5, 3),
                Slot.create(GladCategory.VEL, 100, 25, 0, 2, 1),
                Slot.create(GladCategory.HORSE, 83, 40, 20, 2, 3),
                Slot.create(GladCategory.HORSE, 73, 20, 20, 1, 3)
        ),
        HORSE_4_10(
                Slot.create(GladCategory.DIM, 98, 10, 10, 5, 3),
                Slot.create(GladCategory.VEL, 100, 0, 0, 2, 1),
                Slot.create(GladCategory.HORSE, 0, 53, 17, 2, 3),
                Slot.create(GladCategory.HORSE, 73, 37, 20, 1, 3)
        ),
        HORSE_4_11(
                Slot.create(GladCategory.DIM, 65, 50, 20, 5, 3),
                Slot.create(GladCategory.VEL, 100, 25, 0, 2, 1),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 2, 3),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 1, 3)
        ),
        HORSE_4_12(
                Slot.create(GladCategory.FRAK, 100, 67, 25, 5, 3),
                Slot.create(GladCategory.VEL, 100, 0, 0, 2, 1),
                Slot.create(GladCategory.HORSE, 73, 67, 23, 2, 3),
                Slot.create(GladCategory.HORSE, 70, 40, 20, 1, 3)
        ),
        HORSE_4_13(
                Slot.create(GladCategory.FRAK, 90, 65, 25, 5, 3),
                Slot.create(GladCategory.VEL, 100, 25, 0, 2, 1),
                Slot.create(GladCategory.HORSE, 83, 40, 20, 2, 3),
                Slot.create(GladCategory.HORSE, 73, 20, 20, 1, 3)
        ),
        HORSE_4_14(
                Slot.create(GladCategory.FRAK, 97, 27, 20, 5, 3),
                Slot.create(GladCategory.VEL, 100, 25, 0, 2, 1),
                Slot.create(GladCategory.HORSE, 0, 53, 17, 2, 3),
                Slot.create(GladCategory.HORSE, 73, 37, 20, 1, 3)
        ),
        HORSE_5_2(
                Slot.create(GladCategory.DIMSEC, 98, 10, 15, 5, 3),
                Slot.create(GladCategory.DIM, 98, 10, 15, 4, 3),
                Slot.create(GladCategory.VEL, 100, 0, 0, 2, 1),
                Slot.create(GladCategory.HORSE, 73, 67, 23, 2, 3),
                Slot.create(GladCategory.HORSE, 70, 40, 20, 1, 3)
        ),
        HORSE_5_3(
                Slot.create(GladCategory.DIMSEC, 98, 10, 15, 5, 3),
                Slot.create(GladCategory.DIM, 98, 10, 15, 4, 3),
                Slot.create(GladCategory.VEL, 100, 25, 0, 2, 1),
                Slot.create(GladCategory.HORSE, 83, 40, 20, 2, 3),
                Slot.create(GladCategory.HORSE, 73, 20, 20, 1, 3)
        ),
        HORSE_5_4(
                Slot.create(GladCategory.DIMSEC, 98, 10, 15, 5, 3),
                Slot.create(GladCategory.FRAK, 98, 67, 30, 4, 3),
                Slot.create(GladCategory.VEL, 100, 0, 0, 2, 1),
                Slot.create(GladCategory.HORSE, 0, 53, 17, 2, 3),
                Slot.create(GladCategory.HORSE, 73, 37, 20, 1, 3)
        ),
        HORSE_5_5(
                Slot.create(GladCategory.DIMSEC, 98, 10, 15, 5, 3),
                Slot.create(GladCategory.FRAK, 98, 67, 30, 4, 3),
                Slot.create(GladCategory.VEL, 100, 25, 0, 2, 1),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 2, 3),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 1, 3)
        ),
        HORSE_5_6(
                Slot.create(GladCategory.DIMSEC, 90, 20, 0, 3, 3),
                Slot.create(GladCategory.DIM, 98, 40, 0, 2, 3),
                Slot.create(GladCategory.FRAK, 100, 67, 70, 1, 3),
                Slot.create(GladCategory.HORSE, 0, 60, 20, 2, 2),
                Slot.create(GladCategory.HORSE, 0, 80, 10, 1, 2)
        ),
        HORSE_5_7(
                Slot.create(GladCategory.DIMSEC, 70, 40, 0, 5, 3),
                Slot.create(GladCategory.DIM, 95, 35, 20, 4, 3),
                Slot.create(GladCategory.FRAK, 100, 70, 40, 3, 3),
                Slot.create(GladCategory.HORSE, 73, 67, 23, 2, 3),
                Slot.create(GladCategory.HORSE, 70, 40, 20, 1, 3)
        ),
        HORSE_5_8(
                Slot.create(GladCategory.DIMSEC, 70, 40, 0, 5, 3),
                Slot.create(GladCategory.DIM, 95, 35, 20, 4, 3),
                Slot.create(GladCategory.FRAK, 100, 70, 40, 3, 3),
                Slot.create(GladCategory.HORSE, 83, 40, 20, 2, 3),
                Slot.create(GladCategory.HORSE, 73, 20, 20, 1, 3)
        ),
        HORSE_5_9(
                Slot.create(GladCategory.DIMSEC, 70, 40, 0, 5, 3),
                Slot.create(GladCategory.DIM, 95, 35, 20, 4, 3),
                Slot.create(GladCategory.FRAK, 100, 70, 40, 3, 3),
                Slot.create(GladCategory.HORSE, 0, 53, 17, 2, 3),
                Slot.create(GladCategory.HORSE, 73, 37, 20, 1, 3)
        ),
        HORSE_5_10(
                Slot.create(GladCategory.DIMSEC, 70, 40, 0, 5, 3),
                Slot.create(GladCategory.DIM, 95, 35, 20, 4, 3),
                Slot.create(GladCategory.FRAK, 100, 70, 40, 3, 3),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 2, 3),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 1, 3)
        ),
        HORSE_5_11(
                Slot.create(GladCategory.DIMSEC, 70, 40, 0, 5, 3),
                Slot.create(GladCategory.DIM, 95, 35, 20, 5, 2),
                Slot.create(GladCategory.FRAK, 100, 70, 40, 4, 3),
                Slot.create(GladCategory.HORSE, 73, 67, 23, 2, 3),
                Slot.create(GladCategory.HORSE, 70, 40, 20, 1, 3)
        ),
        HORSE_5_12(
                Slot.create(GladCategory.DIMSEC, 70, 40, 0, 5, 3),
                Slot.create(GladCategory.DIM, 95, 35, 20, 5, 2),
                Slot.create(GladCategory.FRAK, 100, 70, 40, 4, 3),
                Slot.create(GladCategory.HORSE, 83, 40, 20, 2, 3),
                Slot.create(GladCategory.HORSE, 73, 20, 20, 1, 3)
        ),
        HORSE_5_13(
                Slot.create(GladCategory.DIMSEC, 70, 40, 0, 5, 3),
                Slot.create(GladCategory.DIM, 95, 35, 20, 5, 2),
                Slot.create(GladCategory.FRAK, 100, 70, 40, 4, 3),
                Slot.create(GladCategory.HORSE, 0, 53, 17, 2, 3),
                Slot.create(GladCategory.HORSE, 73, 37, 20, 1, 3)
        ),
        HORSE_5_14(
                Slot.create(GladCategory.DIMSEC, 70, 40, 0, 5, 3),
                Slot.create(GladCategory.DIM, 95, 35, 20, 5, 2),
                Slot.create(GladCategory.FRAK, 100, 70, 40, 4, 3),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 2, 3),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 1, 3)
        ),
        HORSE_5_15(
                Slot.create(GladCategory.DIMSEC, 100, 0, 0, 5, 3),
                Slot.create(GladCategory.DIM, 100, 0, 0, 5, 2),
                Slot.create(GladCategory.FRAK, 100, 0, 0, 4, 3),
                Slot.create(GladCategory.HORSE, 100, 100, 0, 2, 3),
                Slot.create(GladCategory.HORSE, 100, 100, 0, 1, 3)
        ),
        HORSE_5_16(
                Slot.create(GladCategory.DIMSEC, 90, 40, 0, 5, 3),
                Slot.create(GladCategory.TELEGA, 100, 0, 0, 1, 3),
                Slot.create(GladCategory.FRAK, 100, 40, 40, 4, 3),
                Slot.create(GladCategory.HORSE, 73, 67, 23, 3, 3),
                Slot.create(GladCategory.HORSE, 70, 40, 20, 2, 3)
        ),
        HORSE_5_17(
                Slot.create(GladCategory.DIM, 97, 10, 10, 5, 3),
                Slot.create(GladCategory.TELEGA, 100, 0, 0, 1, 3),
                Slot.create(GladCategory.FRAK, 100, 67, 70, 4, 3),
                Slot.create(GladCategory.HORSE, 83, 40, 20, 3, 3),
                Slot.create(GladCategory.HORSE, 73, 20, 20, 2, 3)
        ),
        HORSE_5_18(
                Slot.create(GladCategory.DIM, 90, 40, 0, 5, 3),
                Slot.create(GladCategory.TELEGA, 100, 0, 0, 1, 3),
                Slot.create(GladCategory.VEL, 100, 25, 0, 2, 1),
                Slot.create(GladCategory.HORSE, 73, 55, 20, 3, 3),
                Slot.create(GladCategory.HORSE, 60, 20, 15, 2, 3)
        ),
        HORSE_5_19(
                Slot.create(GladCategory.DUELIST, 95, 50, 50, 5, 2),
                Slot.create(GladCategory.DUELIST, 95, 50, 50, 5, 3),
                Slot.create(GladCategory.DUELIST, 95, 50, 50, 5, 4),
                Slot.create(GladCategory.HORSE, 73, 55, 20, 2, 3),
                Slot.create(GladCategory.HORSE, 60, 20, 15, 1, 3)
        ),
        HORSE_6_2(
                Slot.create(GladCategory.DIMSEC, 98, 10, 15, 5, 3),
                Slot.create(GladCategory.DIM, 98, 10, 15, 4, 3),
                Slot.create(GladCategory.VEL, 100, 0, 0, 2, 1),
                Slot.create(GladCategory.RET, 100, 30, 30, 4, 2),
                Slot.create(GladCategory.HORSE, 73, 67, 23, 2, 3),
                Slot.create(GladCategory.HORSE, 70, 40, 20, 1, 3)
        ),
        HORSE_6_3(
                Slot.create(GladCategory.DIMSEC, 98, 10, 15, 5, 3),
                Slot.create(GladCategory.DIM, 98, 10, 15, 4, 3),
                Slot.create(GladCategory.VEL, 100, 25, 0, 2, 1),
                Slot.create(GladCategory.RET, 100, 30, 30, 4, 2),
                Slot.create(GladCategory.HORSE, 83, 40, 20, 2, 3),
                Slot.create(GladCategory.HORSE, 73, 20, 20, 1, 3)
        ),
        HORSE_6_4(
                Slot.create(GladCategory.DIMSEC, 98, 10, 15, 5, 3),
                Slot.create(GladCategory.FRAK, 98, 67, 30, 4, 3),
                Slot.create(GladCategory.VEL, 100, 0, 0, 2, 1),
                Slot.create(GladCategory.RET, 100, 30, 30, 4, 2),
                Slot.create(GladCategory.HORSE, 0, 53, 17, 2, 3),
                Slot.create(GladCategory.HORSE, 73, 37, 20, 1, 3)
        ),
        HORSE_6_5(
                Slot.create(GladCategory.DIMSEC, 98, 10, 15, 5, 3),
                Slot.create(GladCategory.FRAK, 98, 67, 30, 4, 3),
                Slot.create(GladCategory.VEL, 100, 25, 0, 2, 1),
                Slot.create(GladCategory.RET, 100, 30, 30, 4, 2),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 2, 3),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 1, 3)
        ),
        HORSE_6_6(
                Slot.create(GladCategory.DIMSEC, 90, 20, 0, 3, 3),
                Slot.create(GladCategory.DIM, 98, 40, 0, 2, 3),
                Slot.create(GladCategory.FRAK, 100, 67, 70, 1, 3),
                Slot.create(GladCategory.RET, 100, 30, 30, 1, 4),
                Slot.create(GladCategory.HORSE, 0, 60, 20, 2, 2),
                Slot.create(GladCategory.HORSE, 0, 80, 10, 1, 2)
        ),
        HORSE_6_7(
                Slot.create(GladCategory.DIMSEC, 70, 40, 0, 5, 3),
                Slot.create(GladCategory.DIM, 95, 35, 20, 4, 3),
                Slot.create(GladCategory.FRAK, 100, 70, 40, 3, 3),
                Slot.create(GladCategory.RET, 100, 30, 30, 4, 2),
                Slot.create(GladCategory.HORSE, 73, 67, 23, 2, 3),
                Slot.create(GladCategory.HORSE, 70, 40, 20, 1, 3)
        ),
        HORSE_6_8(
                Slot.create(GladCategory.DIMSEC, 70, 40, 0, 5, 3),
                Slot.create(GladCategory.DIM, 95, 35, 20, 4, 3),
                Slot.create(GladCategory.FRAK, 100, 70, 40, 3, 3),
                Slot.create(GladCategory.RET, 100, 30, 30, 4, 2),
                Slot.create(GladCategory.HORSE, 83, 40, 20, 2, 3),
                Slot.create(GladCategory.HORSE, 73, 20, 20, 1, 3)
        ),
        HORSE_6_9(
                Slot.create(GladCategory.DIMSEC, 70, 40, 0, 5, 3),
                Slot.create(GladCategory.DIM, 95, 35, 20, 4, 3),
                Slot.create(GladCategory.FRAK, 100, 70, 40, 3, 3),
                Slot.create(GladCategory.RET, 100, 30, 30, 4, 2),
                Slot.create(GladCategory.HORSE, 0, 53, 17, 2, 3),
                Slot.create(GladCategory.HORSE, 73, 37, 20, 1, 3)
        ),
        HORSE_6_10(
                Slot.create(GladCategory.DIMSEC, 70, 40, 0, 5, 3),
                Slot.create(GladCategory.DIM, 95, 35, 20, 4, 3),
                Slot.create(GladCategory.FRAK, 100, 70, 40, 3, 3),
                Slot.create(GladCategory.RET, 100, 30, 30, 4, 2),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 2, 3),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 1, 3)
        ),
        HORSE_6_11(
                Slot.create(GladCategory.DIMSEC, 70, 40, 0, 5, 3),
                Slot.create(GladCategory.DIM, 95, 35, 20, 5, 2),
                Slot.create(GladCategory.FRAK, 100, 70, 40, 4, 3),
                Slot.create(GladCategory.RET, 100, 30, 30, 4, 2),
                Slot.create(GladCategory.HORSE, 73, 67, 23, 2, 3),
                Slot.create(GladCategory.HORSE, 70, 40, 20, 1, 3)
        ),
        HORSE_6_12(
                Slot.create(GladCategory.DIMSEC, 70, 40, 0, 5, 3),
                Slot.create(GladCategory.DIM, 95, 35, 20, 5, 2),
                Slot.create(GladCategory.FRAK, 100, 70, 40, 4, 3),
                Slot.create(GladCategory.RET, 100, 30, 30, 4, 2),
                Slot.create(GladCategory.HORSE, 83, 40, 20, 2, 3),
                Slot.create(GladCategory.HORSE, 73, 20, 20, 1, 3)
        ),
        HORSE_6_13(
                Slot.create(GladCategory.DIMSEC, 70, 40, 0, 5, 3),
                Slot.create(GladCategory.DIM, 95, 35, 20, 5, 2),
                Slot.create(GladCategory.FRAK, 100, 70, 40, 4, 3),
                Slot.create(GladCategory.RET, 100, 30, 30, 4, 2),
                Slot.create(GladCategory.HORSE, 0, 53, 17, 2, 3),
                Slot.create(GladCategory.HORSE, 73, 37, 20, 1, 3)
        ),
        HORSE_6_14(
                Slot.create(GladCategory.DIMSEC, 70, 40, 0, 5, 3),
                Slot.create(GladCategory.DIM, 95, 35, 20, 5, 2),
                Slot.create(GladCategory.FRAK, 100, 70, 40, 4, 3),
                Slot.create(GladCategory.RET, 100, 30, 30, 4, 2),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 2, 3),
                Slot.create(GladCategory.HORSE, 100, 100, 20, 1, 3)
        ),
        HORSE_6_15(
                Slot.create(GladCategory.DIMSEC, 100, 0, 0, 5, 3),
                Slot.create(GladCategory.DIM, 100, 0, 0, 5, 2),
                Slot.create(GladCategory.FRAK, 100, 0, 0, 4, 3),
                Slot.create(GladCategory.RET, 100, 30, 30, 4, 2),
                Slot.create(GladCategory.HORSE, 100, 100, 0, 2, 3),
                Slot.create(GladCategory.HORSE, 100, 100, 0, 1, 3)
        ),
        HORSE_6_16(
                Slot.create(GladCategory.DIMSEC, 90, 40, 0, 5, 3),
                Slot.create(GladCategory.TELEGA, 100, 0, 0, 1, 3),
                Slot.create(GladCategory.FRAK, 100, 40, 40, 4, 3),
                Slot.create(GladCategory.RET, 100, 30, 30, 4, 2),
                Slot.create(GladCategory.HORSE, 73, 67, 23, 3, 3),
                Slot.create(GladCategory.HORSE, 70, 40, 20, 2, 3)
        ),
        HORSE_6_17(
                Slot.create(GladCategory.DIM, 97, 10, 10, 5, 3),
                Slot.create(GladCategory.TELEGA, 100, 0, 0, 1, 3),
                Slot.create(GladCategory.FRAK, 100, 67, 70, 4, 3),
                Slot.create(GladCategory.RET, 100, 30, 30, 4, 2),
                Slot.create(GladCategory.HORSE, 83, 40, 20, 3, 3),
                Slot.create(GladCategory.HORSE, 73, 20, 20, 2, 3)
        ),
        HORSE_6_18(
                Slot.create(GladCategory.DIM, 90, 40, 0, 5, 3),
                Slot.create(GladCategory.TELEGA, 100, 0, 0, 1, 3),
                Slot.create(GladCategory.VEL, 100, 25, 0, 2, 1),
                Slot.create(GladCategory.RET, 100, 30, 30, 4, 3),
                Slot.create(GladCategory.HORSE, 73, 55, 20, 3, 3),
                Slot.create(GladCategory.HORSE, 60, 20, 15, 2, 3)
        ),
        HORSE_7_1(
                Slot.create(GladCategory.DIM, 97, 15, 15, 5, 1),
                Slot.create(GladCategory.FRAK, 97, 40, 25, 5, 2),
                Slot.create(GladCategory.SEC, 97, 15, 15, 5, 3),
                Slot.create(GladCategory.DIM, 97, 15, 15, 5, 4),
                Slot.create(GladCategory.FRAK, 97, 40, 25, 5, 5),
                Slot.create(GladCategory.HORSE, 70, 40, 13, 1, 2),
                Slot.create(GladCategory.HORSE, 60, 20, 20, 1, 4)
        ),
        HORSE_7_2(
                Slot.create(GladCategory.SEC, 97, 15, 15, 5, 3),
                Slot.create(GladCategory.FRAK, 97, 40, 25, 4, 3),
                Slot.create(GladCategory.DIM, 97, 15, 15, 5, 4),
                Slot.create(GladCategory.RET, 97, 30, 30, 4, 4),
                Slot.create(GladCategory.TELEGA, 100, 0, 0, 1, 3),
                Slot.create(GladCategory.HORSE, 70, 40, 13, 3, 3),
                Slot.create(GladCategory.HORSE, 60, 20, 20, 2, 3)
        );

    */

    //private Slot[] slots;
    private Slot [] slots;
    private int priority;
    private int group;
    private boolean left;
    private boolean right;

//	Scheme(Slot ...slots) {
//		this.slots = slots;
//	}

    public Scheme(Slot... slots) {
        this.slots = slots;
    }

//	public Slot[] getSlots() {
//		return slots;
//	}


    public Slot[] getSlots() {
        return slots;
    }

    public void invert() {
        for (Slot slot : getSlots()) {
            slot.invert();
        }
    }

    public List<CGlad> fill(GladPool gp) {
        List<CGlad> glads = new ArrayList<CGlad>();
            for (Slot slot : getSlots()) {
                CGlad acquire = gp.acquire(slot.getCategory());
                if (acquire != null) {
                    slot.setGlad(acquire);
                    glads.add(acquire);
                } else {
                    //Utils.soutn("!!!!!!!!!!Acquired null from gp " + gp.toString() + " for slot " + slot.toString() + " category " + slot.getCategory() );
                    //slot.setGlad(gp.acquire(GladCategory.MAIN_TYPE));
                }
            }
        return glads;
    }

    public Cxml getPlacement(long btlId, boolean invert) {
        Cxml cxml = new Cxml();
        cxml.setId(btlId);
            for (Slot slot : getSlots()) {
                if (slot.getGlad() == null) continue;
                Cgl gl = new Cgl();
                gl.setId(slot.getGlad().getId());
                gl.setAttack(slot.getAttack() == -1 ? 99 : slot.getAttack());
                gl.setPower(slot.getPower() == -1 ? 50 : slot.getPower());
                gl.setBlock(slot.getBlock() == -1 ? 50 : slot.getBlock());
                //gl.setCourage(gl.getAttack() <= 50 ? "" : "50");
                gl.setCourage("50");
                gl.setX(slot.getX());
                gl.setY(invert ? 6 - slot.getY() : slot.getY());
                gl.setArmorid(slot.getGlad().getArmorId());
                cxml.getGls().add(gl);
            }
        return cxml;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getGroup() {
        return group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Scheme)) return false;

        Scheme scheme = (Scheme) o;

        if (group != scheme.group) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return group;
    }

    public int getPriority() {
        return priority;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isLeft() {
        return left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isRight() {
        return right;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[priority=" + priority + "]\n");
        for (Slot slot : slots) {
            sb.append(slot).append("\n");
        }
        return sb.toString();
    }
}
