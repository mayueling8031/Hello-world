import java.text.SimpleDateFormat;
import java.util.*;

//业务类
public class MemberDao {
    Scanner input = new Scanner(System.in);
    List<Member> memberList = new ArrayList<Member>();

    //开始菜单
    public void menu(){
        System.out.println("********************欢迎进入超市会员管理系统********************");
        System.out.println("1.积分累计  2.积分兑换   3.查询剩余积分   4.修改密码   5.开卡   6.退出");
        System.out.println("**************************************************************");
        System.out.println("请选择：");
    }
    public void start(){
        do {
            menu();
            int choose = input.nextInt();
            switch (choose){
                case 1:
                    if (saveScore()){
                        System.out.println("积分累计成功！");
                    }else{
                        System.out.println("积分累计失败！");
                }
                continue;
                case 2:
                    if (minusScore()){
                        System.out.println("积分兑换成功！");
                    }else {
                        System.out.println("积分兑换失败！");
                    }
                continue;
                case 3:
                    showScore();
                    continue;
                case 4:
                    changePwd();
                 continue;
                case 5:
                    register();
                 continue;
                case 6:
                    System.out.println("感谢你的使用，欢迎下次再来！");
                break;
                default:
                    System.out.println("操作有误，请重新选择！");
                    continue;
            }
            break;
        }while (true);
    }
    //查询会员是否存在
    public  Member hasMember(int id,String pwd){
        for (Member member:memberList){
            if (member.getCardId()==id&&member.getPassword().equals(pwd)){
                return member;
            }
        }
        return null;
    }

    //积分累计
    public boolean saveScore(){
        System.out.println("请输入会员卡号：");
        int id = input.nextInt();
        System.out.println("请输入会员密码：");
        String pwd = input.next();
        Member member = hasMember(id,pwd);
        boolean flag = true;
        if (member!=null){
            System.out.println("请输入你消费的金额（1元积1分）：");
            int score = input.nextInt();
             member.setScore(member.getScore()+score);
            System.out.println("你当前积分为:"+member.getScore());
            flag = true;
        }else {
            System.out.println("该会员卡不存在");
            flag = false;
        }
        return true;
    }

    //积分兑换
    public boolean minusScore(){
        System.out.println("请输入会员卡号：");
        int id = input.nextInt();
        System.out.println("请输入会员密码：");
        String pwd = input.next();
        Member member = hasMember(id,pwd);
        boolean flag;
        if (member!=null){
            System.out.println("请输入你需要兑换的积分数（1元积1分）：");
            int score = input.nextInt();
            if(score<=member.getScore()){
                member.setScore(member.getScore()-score);
                System.out.println("你此次抵消："+score/100*0.1+"元");
            }else {
                System.out.println("积分数不足，无法兑换");
            }
            member.setScore(member.getScore()+score);
            System.out.println("你当前积分为:"+(member.getScore()-score));
            flag = true;
        }else {
            System.out.println("该会员卡不存在,无法兑换");
            flag = false;
        }
        return flag;
    }

    //积分查询
    public void showScore(){
        System.out.println("请输入会员卡号：");
        int id = input.nextInt();
        System.out.println("请输入会员密码：");
        String pwd = input.next();
        Member member = hasMember(id,pwd);
        if(member!=null){
            System.out.println("会员卡号   姓名    剩余积分    开卡日期");
            System.out.println(member.getCardId()+"\t"+member.getName()+"\t\t"+member.getScore()+"\t\t\t"+member.getRegisDate());
        }else {
            System.out.println("无此无会员");
        }
    }

    //修改密码
    public boolean changePwd(){
        System.out.println("请输入会员卡号：");
        int id = input.nextInt();
        System.out.println("请输入会员密码：");
        String pwd = input.next();
        Member member = hasMember(id,pwd);
        String newPwd ;
        boolean flag = true;
            if (member!=null){
                    System.out.println("请输入新密码：");
                    newPwd = input.next();
                    if(newPwd!=member.getPassword()&&newPwd.length()>=6){
                        member.setPassword(newPwd);
                     System.out.println("密码修改成功");
                        flag = false;
                    }else {
                        flag = true;
                        System.out.println("密码重复，请重新输入！");
                    }
            }else{
                System.out.println("无此会员卡");
            }
        return flag;
    }

    //生成随机开好
    public int createId(){
        Random random = new Random();
        int id = random.nextInt(9999999);
        for (Member member:memberList){
            if (member.getCardId()==id){
                id = random.nextInt(9999999);
            }
        }
        return id;
    }
    //开卡
    public void register(){
        Member member = new Member();
        System.out.println("请输入注册姓名：");
        member.setName(input.next());
        //会员卡随机生成
        int carId = createId();
        member.setCardId(carId);
        System.out.println("请输入注册密码：");
        String pwd;
        //保证用户密码正确
        boolean flag = true;
        do {
            pwd = input.next();
            if (pwd.length()<6){
                System.out.println("会员密码不能少于六位！请重新输入：");
                flag = false;
            }else {
                flag = true;
                member.setPassword(pwd);
            }
        }while (!flag);
        //开卡默认送积分100
        member.setScore(100);
        //开卡日期
        Date date = new Date();
        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
        String sdate = sfd.format(date);
        member.setRegisDate(sdate);
        System.out.println("恭喜，会员卡开通成功，系统赠送100积分，你的会员卡好："+carId);
        memberList.add(member);

    }
}
