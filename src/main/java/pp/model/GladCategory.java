package pp.model;

import pp.model.comparators.*;
import pp.model.enums.GladType;
import pp.model.xml.CGlad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public enum GladCategory implements ComparatorProvider<CGlad> {

    SLIV(Arrays.<GladType>asList(GladType.Secutor)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new SlivComparator();
        }
    },
    SEC(Arrays.<GladType>asList(GladType.Secutor)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                    new CGladLvlComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
                    );
        }
    },
    SEC1(Arrays.<GladType>asList(GladType.Secutor), true) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladCanTankComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    SEC2(Arrays.<GladType>asList(GladType.Secutor), false) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladCanDamageComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    HOP(Arrays.<GladType>asList(GladType.Hoplomachus)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    HOP1(Arrays.<GladType>asList(GladType.Hoplomachus), true) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladCanTankComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    HOP2(Arrays.<GladType>asList(GladType.Hoplomachus), false) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladCanDamageComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    MUR(Arrays.<GladType>asList(GladType.Murmillon)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    MUR1(Arrays.<GladType>asList(GladType.Murmillon), true) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladCanTankComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    MUR2(Arrays.<GladType>asList(GladType.Murmillon), false) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladCanDamageComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0)
            );
        }
    },
    DIM(Arrays.<GladType>asList(GladType.Dimachaerus)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    DIM1(Arrays.<GladType>asList(GladType.Dimachaerus), true) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladCanTankComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    DIM2(Arrays.<GladType>asList(GladType.Dimachaerus), false) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladCanDamageComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    FRAK(Arrays.<GladType>asList(GladType.Thraex)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    FRAK1(Arrays.<GladType>asList(GladType.Thraex), true) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladCanTankComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    FRAK2(Arrays.<GladType>asList(GladType.Thraex), false) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladCanDamageComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    RET(Arrays.<GladType>asList(GladType.Retiarius)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    RET1(Arrays.<GladType>asList(GladType.Retiarius), true) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladCanTankComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    RET2(Arrays.<GladType>asList(GladType.Retiarius), false) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladCanDamageComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    VEL(Arrays.<GladType>asList(GladType.Velit)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    TANK(Arrays.asList(GladType.values())) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladCanTankComparator(),
                    new GladTypeComparator(Arrays.<GladType>asList(GladType.Secutor, GladType.Murmillon, GladType.Dimachaerus, GladType.Thraex, GladType.Hoplomachus)),
                    new GladHpComparator(),
                    new GladTypeComparator(Arrays.<GladType>asList(GladType.Secutor, GladType.Murmillon)),
                    new GladTypeComparator(Arrays.<GladType>asList(GladType.Dimachaerus, GladType.Thraex)),
                    new GladTypeComparator(Arrays.<GladType>asList(GladType.Hoplomachus)),
                    new GladExpComparator(true)
            );
        }
    },
    SPEED(Arrays.<GladType>asList(GladType.Secutor, GladType.Dimachaerus, GladType.Hoplomachus)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladHpComparator(),
                    new GladExpComparator(true)
            );
        }
    },
    EVASION(Arrays.<GladType>asList(GladType.Secutor, GladType.Murmillon, GladType.Dimachaerus, GladType.Thraex)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladHpComparator(),
                    new GladExpComparator(true)
            );
        }
    },
    BLOCK(Arrays.<GladType>asList(GladType.Murmillon, GladType.Thraex, GladType.Hoplomachus)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladHpComparator(),
                    new GladExpComparator(true)
            );
        }
    },
    NOVEL(Arrays.<GladType>asList(GladType.Secutor, GladType.Murmillon, GladType.Dimachaerus, GladType.Thraex, GladType.Hoplomachus, GladType.Retiarius)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                    new GladHpComparator()
            );
        }
    },
    MECH(Arrays.<GladType>asList(GladType.Secutor, GladType.Murmillon, GladType.Dimachaerus, GladType.Thraex, GladType.Hoplomachus)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladHpComparator(),
                    new GladExpComparator(true)
            );
        }
    },
    DEFENDER(Arrays.asList(GladType.values())) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladCanTankComparator(),
                    new GladTypeComparator(Arrays.<GladType>asList(GladType.Murmillon, GladType.Hoplomachus)),
                    new GladHpComparator(),
                    new GladTypeComparator(Arrays.<GladType>asList(GladType.Hoplomachus)),
                    new GladTypeComparator(Arrays.<GladType>asList(GladType.Murmillon)),
                    new GladExpComparator(true)
            );
        }
    },
    DAMAGER(Arrays.asList(GladType.values())) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladTypeComparator(Arrays.<GladType>asList(GladType.Thraex, GladType.Dimachaerus, GladType.Retiarius)),
                    new GladNotDDMutantComparator(),
                    new GladHpComparator(),
                    new GladExpComparator(true)
            );
        }
    },
    HOR(Arrays.<GladType>asList(GladType.Horseman)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    HOR1(Arrays.<GladType>asList(GladType.Horseman)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladCanTankComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    HOR2(Arrays.<GladType>asList(GladType.Horseman)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladCanDamageComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    SAG(Arrays.<GladType>asList(GladType.Archer)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    DUELIST(Arrays.asList(GladType.values())) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    //new GladArmorCountComparator(),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    TEL(Arrays.<GladType>asList(GladType.Chariot)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    DIMSEC(Arrays.<GladType>asList(GladType.Dimachaerus, GladType.Secutor)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                     
                    new CGladLvlComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    CHOSEN(Arrays.<GladType>asList(GladType.values())) {
        @Override
        public Comparator<CGlad> cmpare(final Long... args) {
            return new Comparator<CGlad>() {
                @Override
                public int compare(CGlad o1, CGlad o2) {
                    Boolean f1 = o1.getId() == (Long) args[1];
                    Boolean f2 = o2.getId() == (Long) args[1];
                    return f1.compareTo(f2);
                }
            };
        }
    },
    CHOSEN2(Arrays.<GladType>asList(GladType.values())) {
        @Override
        public Comparator<CGlad> cmpare(final Long... args) {
            return new Comparator<CGlad>() {
                @Override
                public int compare(CGlad o1, CGlad o2) {
                    Boolean f1 = o1.getId() == (Long) args[2];
                    Boolean f2 = o2.getId() == (Long) args[2];
                    return f1.compareTo(f2);
                }
            };
        }
    },
    MAIN_TYPE(GladType.mainTypes()) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(

                    new GladHpComparator()
            );
        }
    },
    MAIN_TYPE_VELHOP(Arrays.asList(GladType.Velit, GladType.Hoplomachus)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                    new GladHpComparator()
            );
        }
    },
    MAIN_TYPE_HOP(Arrays.asList(GladType.Hoplomachus)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                    new CGladLvlComparator(),
                    new GladHpBarrierComparator((Long) args[0]),
                    new GladMoraleComparator(args[args.length - 1] > 0),
                    new GladExpComparator(true)
            );
        }
    },
    MAIN_TYPE_NOVELHOP(Arrays.asList(GladType.Dimachaerus, GladType.Murmillon, GladType.Secutor, GladType.Thraex)) {
        @Override
        public Comparator<CGlad> cmpare(Long... args) {
            return new CombinationComparator(
                    new GladHpComparator()
            );
        }
    },

    ;
    private Collection<GladType> restriction;
    private Boolean tank;

    private GladCategory(Collection<GladType> restriction) {
        this.restriction = restriction;
    }

    GladCategory(List<GladType> gladTypes, boolean tank) {
        this(gladTypes);
        this.tank = tank;

    }

    public List<CGlad> filter(Collection<CGlad> glads) {
        ArrayList<CGlad> result = new ArrayList<CGlad>();
        for (CGlad glad : glads) {
            for (GladType gladType : restriction) {
                if (glad.getTypeid() == gladType.getId()) {
                    if (tank == null || tank.booleanValue() == canTank(glad)) {
                        result.add(glad);
                        break;
                    }
                }
            }
        }
        return result;
    }

    private boolean canTank(CGlad g) {
        return g.getVit() + g.getDex() > (g.getAcc() + g.getStr()) * 1.2;
    }
    public static ComparatorProvider valueOfExtended(final String name) {
        if (name.toLowerCase().startsWith("id")) {
            final long id = Long.parseLong(name.substring(2));
            return new ComparatorProvider<CGlad>(){
                @Override
                public Comparator<CGlad> cmpare(Long... args) {
                    return new Comparator<CGlad>() {
                        @Override
                        public int compare(CGlad o1, CGlad o2) {
                            Boolean f1 = o1.getId() == id;
                            Boolean f2 = o2.getId() == id;
                            return f1.compareTo(f2);
                        }
                    };
                }

                @Override
                public List<CGlad> filter(Collection<CGlad> glads) {
                    return new ArrayList<CGlad>(glads);
                }
            };
        } else return valueOf(name);
    }

    @Override
    public String toString() {
        return "GladCategory{" +
                "restriction=" + restriction +
                ", tank=" + tank +
                '}';
    }
}
