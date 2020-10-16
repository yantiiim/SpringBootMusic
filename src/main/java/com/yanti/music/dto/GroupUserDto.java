package com.yanti.music.dto;

public class GroupUserDto {
    
    public static class NewRegister{

        private String id;
        private String idUser;
        private Integer idGruop;
        private String username;
        private String roleName;

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdUser() {
            return this.idUser;
        }

        public void setIdUser(String idUser) {
            this.idUser = idUser;
        }

        public Integer getIdGruop() {
            return this.idGruop;
        }

        public void setIdGruop(Integer idGruop) {
            this.idGruop = idGruop;
        }

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRoleName() {
            return this.roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

    }

    public static class Information{

        private String id;
        private String idUser;
        private Integer idGruop;

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdUser() {
            return this.idUser;
        }

        public void setIdUser(String idUser) {
            this.idUser = idUser;
        }

        public Integer getIdGruop() {
            return this.idGruop;
        }

        public void setIdGruop(Integer idGruop) {
            this.idGruop = idGruop;
        }

    }
}
