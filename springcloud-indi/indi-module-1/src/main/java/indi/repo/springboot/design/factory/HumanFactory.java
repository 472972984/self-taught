package indi.repo.springboot.design.factory;

/**
 * 功能说明: HumanFactory为实现类，完成具体的任务——创建人类
 *
 * @author: ChenHQ
 * @date: 2021/8/9
 * @desc:
 */
public class HumanFactory extends AbstractHumanFactory {

    @Override
    public <T extends Human> T createHuman(Class<T> c) {
        //定义一个生产的人种
        Human human = null;
        try{
            //产生一个人种
            human = (T)Class.forName(c.getName()).newInstance();
        }catch(Exception e){
            System.out.println("人种生成错误！");
        }
        return (T)human;
    }

    public static void main(String[] args) {

        AbstractHumanFactory YinYangLu = new HumanFactory();
        WriteHuman writeHuman = YinYangLu.createHuman(WriteHuman.class);
        writeHuman.getColor();
        writeHuman.talk();

        YellowHuman yellowHuman = YinYangLu.createHuman(YellowHuman.class);
        yellowHuman.getColor();
        yellowHuman.talk();

        BlackHuman blackHuman = YinYangLu.createHuman(BlackHuman.class);
        blackHuman.getColor();
        blackHuman.talk();


    }

}
