const totalCount = document.querySelector("#totalCount");
const completeCount = document.querySelector("#completeCount");
const reloadBtn = document.querySelector("#reloadBtn");

const todoTitle=document.querySelector("#todoTitle")
const todoContent=document.querySelector("#todoContent")
const addBtn=document.querySelector("#addBtn")

//전체 Todo개수 조회및 출력하는 함수
function getTotalCount(){  //함수정의

    //비동기로 서버에서 전체 TOdo 개수 조회하는 코드 작성
  
    fetch("/ajax/totalCount")
    .then(response=>{
        console.log(response);
        //console.log(response.text());

        return response.text();
    })


    //두번째 then의 매개변수 ==첫번째 덴에서 반환된 프로미스객체의 리절트값
    .then(result=>{
        //리절트매개변수 ==컨트롤러메서드에서 반환된값
        console.log("result",result);
        totalCount.innerText=result;

    })

}

//completecount 값 비동기 통신으로 얻어와서 화면출력
function getCompleteCount(){

    fetch("/ajax/completeCount")
    .then(response=> {return response.text()})
    .then(result =>{
        completeCount.innerText = result;
    })

}

//새로고침버튼이 클릭되었을때
reloadBtn.addEventListener('click',()=>{
    getTotalCount();
    getCompleteCount();
});

//할일 추가버튼 클릭시 동작
addBtn.addEventListener('click',()=>{

    //비동기로 할일추가하는 fetch() API코드작성
    //-요청주소:"/ajax/add"
    //-데이터 전달방식 POST방식

    //파라미터를 저장한 JS객체
    const param = {"todoTitle" : todoTitle.value,
                  "todoContent" : todoContent.value    
                }

    fetch("/ajax/add", {
    //key : value
        method : "POST",
        headers : {"Content-Type" : "application/json"}, 
        body :  JSON.stringify(param)  //파람객체를 제이슨으로변환
        
    })
    .then(resp=>resp.text()) //반환된값을 text로 변환
    .then(temp =>{
        if(temp>0){
            alert("추가성공!!");

            todoTitle.value = "";
            todoContent.value="";

            getTotalCount();
        }else{
            alert("추가실패...");
        }   
    
    })
});















//페이지로딩시 바로실행
getTotalCount();
getCompleteCount();