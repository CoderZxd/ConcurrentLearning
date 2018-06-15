package enumtest;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: ConcurrentLearning
 * @Package enumtest
 * @description: TODO:一句话描述信息
 * @Version 1.0
 * @create 2018-06-15 22:54
 **/
public class EnumTest {

    enum Size{

        SAMLL("S"),MEDIUM("M"),LARGE("L"),EXTRALARGE("XL");

        private String abbreviation;

        Size(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public void setAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getAbbreviation() {
            return abbreviation;
        }
    }

    public static void main(String[] args){
        Size size  = Size.LARGE;
        System.out.println(size);
        System.out.println(size.getAbbreviation());
        size = Enum.valueOf(Size.class,"MEDIUM");
        System.out.println(size == Size.MEDIUM);
    }
}
