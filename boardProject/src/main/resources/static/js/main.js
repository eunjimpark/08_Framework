/* 쿠키에서 key가 일치하는 value 얻어오기 함수 */

// 쿠키는 "K=V; K=V;" 형식

// 배열.map(함수) : 배열의 각 요소를 이용해 함수 수행 후
//                  결과 값으로 새로운 배열을 만들어서 반환
const getCookie = key => {
    const cookies = document.cookie; // "K=V; K=V"
  
    // cookies 문자열을 배열 형태로 변환
    const cookieList = cookies.split("; ")  // ["K=V", "K=V"]
                      .map( el => el.split("=") ); // ["K", "V"]
  
    // 배열 -> 객체로 변환 (그래야 다루기 쉽다)
  
    const obj = {}; // 비어있는 객체 선언
  
    for(let i=0 ; i<cookieList.length ; i++){
      const k = cookieList[i][0];  //키값
      const v = cookieList[i][1];
      obj[k] = v;
    }

    //console.log("obj", obj);
    return obj[key];
    
  }

  const loginEmail=document.querySelector("#loginForm input[name='memberEmail']");

  if(loginEmail !=null){

    // 쿠기 중 key 값이 "saveId"인 요소의 value 얻어오기
    const saveId = getCookie("saveId"); //  undefined 또는 이메일

 // saveId 값이 있을 경우
 if(saveId != undefined){
   loginEmail.value = saveId; // 쿠키에서 얻어온 값을 input에 value로 세팅

   // 아이디 저장 체크박스에 체크 해두기
   document.querySelector("input[name='saveId']").checked = true;
 }
  }

  //이메일 ,비번 미작성시 로그인막기
  const loginForm = document.querySelector("#loginForm");
  const loginPw=document.querySelector("#loginForm input[name='memberPw']");

  //#loginForm이 화면에 존재할때 (로그인상태아닐때)
  if(loginForm != null){
    //제출이벤트발생시
    loginForm.addEventListener("submit", e=>{

        if(loginEmail.value.trim().length===0){
            alert("이메일을 작성해 주세요!");
            e.preventDefault();  //기본이벤트 막기
            loginEmail.focus(); //초점이동
            return;
        }
        if(loginPw.value.trim().length===0){
            alert("비밀번호를 작성해 주세요!");
            e.preventDefault();  //기본이벤트 막기
            loginPw.focus(); //초점이동
            return;
        }
    });
  }