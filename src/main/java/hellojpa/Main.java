package hellojpa;

import hellojpa.entity.Member;
import hellojpa.entity.MemberType;
import hellojpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //팀 저장
            Team team = new Team();
            team.setName("팀");
            em.persist(team);

            //회원 저장
            Member member = new Member();
            member.setName("안녕하세요");
            member.setAge(20);
            member.setTeam(team);
            member.setMemberType(MemberType.USER);
            em.persist(member);

            em.flush();
            em.clear();

            //Lazy Fetch 확인
            Member findMember = em.find(Member.class, member.getId());
            Team findTeam = findMember.getTeam();
            String teamName = findTeam.getName();



            List<Member> members = findTeam.getMembers();
            for (Member member1 : members) {
                System.out.println("member1 = " + member1);
            }

            System.out.println(teamName);



            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
