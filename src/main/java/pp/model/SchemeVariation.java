package pp.model;

import pp.StuffManager;
import pp.model.enums.GladType;
import pp.model.xml.CGlad;

import java.util.Arrays;
import java.util.List;

public class SchemeVariation implements Comparable<SchemeVariation> {
    public Scheme scheme;
    public int healable;
    public int needheal;
    public int needmorale;
    public int needcure;
    public List<CGlad> gladstogo;

    public SchemeVariation(Scheme scheme, List<CGlad> gladsToGo, int healable, int needheal, int needmorale, int needcure) {
        this.scheme = scheme;
        this.healable = healable;
        this.gladstogo = gladsToGo;
        this.needheal = needheal;
        this.needmorale = needmorale;
        this.needcure = needcure;
    }


    @Override
    public int compareTo(SchemeVariation o) {
        return Integer.valueOf(needheal + needmorale + needcure * 10).compareTo(o.needheal + o.needmorale + o.needcure * 10);
    }

    @Override
    public String toString() {
        return Arrays.deepToString(gladstogo.toArray());
/*
        return "SchemeVariation{" +
                "scheme=" + scheme +
                ", healable=" + healable +
                ", needheal=" + needheal +
                ", needmorale=" + needmorale +
                ", needcure=" + needcure +
                '}' + '\n';
*/
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchemeVariation)) return false;

        SchemeVariation that = (SchemeVariation) o;

        if (!scheme.equals(that.scheme)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return scheme.hashCode();
    }
}
