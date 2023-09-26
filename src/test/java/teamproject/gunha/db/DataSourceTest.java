package teamproject.gunha.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class DataSourceTest {

   
  @Autowired
  private DataSource dataSource;

  @Test
  @Transactional
  @DisplayName("DB 연결 테스트")
  void testDateSource() {
    assertNotNull(dataSource);
    log.info("DS=" + dataSource);

    try(Connection conn = dataSource.getConnection()) {
      log.info("conn=" + conn);
      assertTrue(conn instanceof Connection);

      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select 100 from dual");
      if(rs.next()){
        assertEquals(100, rs.getLong(1));
      }
    } catch (Exception e) {

    }

  }

  
}
