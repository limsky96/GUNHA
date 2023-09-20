window.onload = () => {
  document.querySelectorAll(".profile-item").forEach(profileNode =>{
    'use strict'

    profileNode.addEventListener("click",async()=>{
      const profileName = profileNode.lastElementChild.dataset.profileName;
      console.log(profileName);
      const requestBody = {
        "selected_name": profileName
      }
      const response = await axios.post("/profile/select", requestBody, {
        "Content-type": "application/json"
      });
      const { data, status } = response;
      if (status === 200) {
        location.href = "/home"
      }
    })
  })

  
}