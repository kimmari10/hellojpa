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
            member.setMemberType(MemberType.USER);
            member.setTeam(team);
            team.getMembers().add(member); //무시됨 그러나 객체지향적 코드유지를 위해 작성을 권장.
            em.persist(member);


//            String jpql = "select m from Member m where m.name like '%hello%'";
            String jpql = "select m from Member m  join fetch m.team"; // 지연 로딩 방지 fetch
            List<Member> result = em.createQuery(jpql, Member.class)
                    .setFirstResult(10)
                    .setMaxResults(20)
                    .getResultList();


            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
