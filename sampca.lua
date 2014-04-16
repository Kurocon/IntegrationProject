-- SAMPCA protocol
-- Declare protocol
sampca_proto = Proto("SAMPCA", "SAMPCA Protocol")

-- create function to dissect
function sampca_proto.dissector(buffer,pinfo,tree)
   pinfo.cols.protocol = "SAMPCA"
   local sVersion = "SAMPCA v" .. buffer(9,1):uint() .. " "
   pinfo.cols.info = sVersion .. ": Unknown Datatype";
   local subtree = tree:add(sampca_proto,buffer(),"SAMPCA Data")

-- Source Addr
   subtree:add(buffer(0,4),"Source Address: " .. buffer(0,1):uint() .. "." .. buffer(1,1):uint() .. "." .. buffer(2,1):uint() .. "." .. buffer(3,1):uint())
-- Destination Addr0
   subtree:add(buffer(4,4),"Destination Address: " .. buffer(4,1):uint() .. "." .. buffer(5,1):uint() .. "." .. buffer(6,1):uint() .. "." .. buffer(7,1):uint())
-- Hop
   subtree:add(buffer(8,1),"Hop count: " .. buffer(8,1):uint())
-- Ver
   subtree:add(buffer(9,1),"Version: " .. buffer(9,1):uint())
-- Datatype
   subtree:add(buffer(10,2),"Datatype: " .. buffer(10,2):uint())
-- Timestamp
   subtree:add(buffer(12,8),"Timestamp: " .. buffer(12,8):uint64())
-- Data length
   subtree:add(buffer(20,2),"Data length: " .. buffer(20,2):uint())
-- Reserved bits
   subtree:add(buffer(22,2),"Reserved bits: " .. buffer(22,2):uint())

-- Switch according to datatype.
   datatype = buffer(10,2):uint()
   
   if datatype == 0 then
      pinfo.cols.info = sVersion .. "ACK " .. buffer(24,8):uint64()
      local inner = subtree:add(sampca_proto,buffer(),"Generic ACK")
      inner:add(buffer(24,8),"ACK for packet #" .. buffer(24,8):uint64())
   elseif datatype == 1 then
      local nickLen = buffer(534,1):uint()
      local hostLen = buffer(535,1):uint()
      local inner = subtree:add(sampca_proto,buffer(),"Broadcast Message")

      pinfo.cols.info = sVersion .. "SYN " .. buffer(12,8):uint64() .. " - Broadcast Message from " .. buffer(24,nickLen):string() .. "@" .. buffer(279,hostLen):string() .. " with IP " .. buffer(0,1):uint() .. "." .. buffer(1,1):uint() .. "." .. buffer(2,1):uint() .. "." .. buffer(3,1):uint()
      inner:add(buffer(24,nickLen),"Nickname: " .. buffer(24,nickLen):string())
      inner:add(buffer(279,hostLen),"Hostname: " .. buffer(279,hostLen):string())
      inner:add(buffer(534,1),"Nickname Length: " .. buffer(534,1):uint())
      inner:add(buffer(535,1),"Hostname Length: " .. buffer(535,1):uint())
   elseif datatype == 2 then
      local chatLen = buffer(20,2):uint()
      pinfo.cols.info = sVersion .. "Chat Message from " .. buffer(0,1):uint() .. "." .. buffer(1,1):uint() .. "." .. buffer(2,1):uint() .. "." .. buffer(3,1):uint() .. ": " .. buffer(24,chatLen):string()
      local inner = subtree:add(sampca_proto,buffer(),"Chat Message")
      inner:add(buffer(24,chatLen),"Message: " .. buffer(24,chatLen):string())
   elseif datatype == 3 then
      local pChatLen = buffer(20,2):uint()
      pinfo.cols.info = sVersion .. "Private Chat Message from " .. buffer(0,1):uint() .. "." .. buffer(1,1):uint() .. "." .. buffer(2,1):uint() .. "." .. buffer(3,1):uint() .. " to " .. buffer(24,1):uint() .. "." .. buffer(25,1):uint() .. "." .. buffer(26,1):uint() .. "." .. buffer(27,1):uint() .. ": " .. buffer(28,pChatLen):string()
      local inner = subtree:add(sampca_proto,buffer(),"Private Chat Message")
      inner:add(buffer(24,4),"Destination IP: " .. buffer(24,1):uint() .. "." .. buffer(25,1):uint() .. "." .. buffer(26,1):uint() .. "." .. buffer(27,1):uint())
      inner:add(buffer(28,pChatLen),"Message: " .. buffer(28,pChatLen):string())
   elseif datatype == 4 or datatype == 5 or datatype == 6 or datatype == 7 or datatype == 8 or datatype == 9 or datatype == 10 or datatype == 11 or datatype == 12 or datatype == 13 or datatype == 14 or datatype == 15 then
      pinfo.cols.info = sVersion .. "Reserved Datatype " .. datatype .. " packet"
   elseif datatype == 16 then
      local fNameLen = buffer(287,1):uint()
      pinfo.cols.info = sVersion .. "Generic file from " .. buffer(0,1):uint() .. "." .. buffer(1,1):uint() .. "." .. buffer(2,1):uint() .. "." .. buffer(3,1):uint() .. " to " .. buffer(4,1):uint() .. "." .. buffer(5,1):uint() .. "." .. buffer(6,1):uint() .. "." .. buffer(7,1):uint()
      local inner = subtree:add(sampca_proto,buffer(),"Generic file")
      inner:add(buffer(24,8),"Packet number " .. buffer(24,4):uint() .. " / " .. buffer(28,4):uint())
      inner:add(buffer(32,fNameLen), "Filename: " .. buffer(32,fNameLen):string())
      inner:add(buffer(288,736), "File Data")
   end

--//    CURRENT_SEQ_NUMBER -- 4 bytes
--//    MAX_SEQ_NUMBER -- 4 bytes
--//    FILENAME -- 255 bytes
--//    FILENAME_LENGTH -- 1 byte

end

-- load the udp.port table
udp_table = DissectorTable.get("udp.port")

-- register our protocol to handle udp port 5555
udp_table:add(5555,sampca_proto)
